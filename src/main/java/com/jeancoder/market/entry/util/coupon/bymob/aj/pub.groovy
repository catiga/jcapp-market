package com.jeancoder.market.entry.util.coupon.bymob.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.entity.CbPubItem
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.JackSonBeanMapper

JCLogger logger = LoggerSource.getLogger();

def pubid = JC.request.param('pubid');

def update = false;
def pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and flag!=?', pubid, -1);

if(!pub) {
	return SimpleAjax.notAvailable('obj_not_found,信息已经删除');
}

if(pub.ss!='0000'&&pub.ss!='1000') {
	return SimpleAjax.notAvailable('status_invalid,状态不支持该操作');
}

def sql = 'select * from CbPubItem where flag!=? and pubid=? and ccid is null';
List<CbPubItem> result = JcTemplate.INSTANCE().find(CbPubItem, sql, -1, pubid);

if(!result) {
	return SimpleAjax.notAvailable('data_empty,尚未添加发券明细信息');
}

pub.ss = '1000';
pub.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
JcTemplate.INSTANCE().update(pub);


//异步执行发券操作
JC.thread.run(5000, {
	logger.info('running')
	def ret = JC.internal.call(SimpleAjax, 'market', '/util/for_pub_coupon', [pubid:pubid]);
	return ret;
}, {
	e->
	logger.info('stop pub:' + JackSonBeanMapper.toJson(e));
	if(e.success) {
		//pub.ss = '2000';
		//JcTemplate.INSTANCE().update(pub);
		JC.internal.call(SimpleAjax, 'market', '/util/modify_pub_ss', [pubid:pubid, ss:'2000']);
	} else {
//		pub.ss = '1010';	//部分发放完成
//		JcTemplate.INSTANCE().update(pub);
		JC.internal.call(SimpleAjax, 'market', '/util/modify_pub_ss', [pubid:pubid, ss:'1010']);
	}
});

return SimpleAjax.available();
