package com.jeancoder.market.internal.wx

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CCTransRec
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.util.JackSonBeanMapper

JCLogger logger = LoggerSource.getLogger();

def pid = JC.internal.param('pid');
def wx_card_id = JC.internal.param('wx_card_id');
def card_code = JC.internal.param('card_code');
def get_partid = JC.internal.param('get_partid');			//当前领取人的openid
def from_partid = JC.internal.param('from_partid');			//如果有的话，是赠送人的openid

logger.info('rec trans cb recodr:' + wx_card_id);

def sql = 'select * from CouponBatch where flag!=? and wxcard_id=? and pid=?';
CouponBatch batch = JcTemplate.INSTANCE().get(CouponBatch, sql, -1, wx_card_id, pid);
if(!batch) {
	return SimpleAjax.notAvailable('param_batch_empty');
}
logger.info('card_code:' + card_code);
//查找coupon code
List<CouponCode> codes = JcTemplate.INSTANCE().find(CouponCode, 'select * from CouponCode where flag!=? and batch_id=? and code=?', -1, batch.id, card_code);
if(codes==null||codes.empty) {
	return SimpleAjax.notAvailable('code_not_found');
} else if(codes.size()>1) {
	return SimpleAjax.notAvailable('code_code_repeat');
}

CouponCode code = codes.get(0);

CCTransRec cc = new CCTransRec();
cc.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
cc.cb_id = batch.id;
cc.cc_id = code.id;
cc.cc_code = card_code;
cc.flag = 0;
cc.get_partid = get_partid;

//查找或者创建领用人
if(!from_partid) {
	//说明是原始领用，增加一条领用记录，直接返回成功
} else {
	//转赠行为，记录转赠与否，并且被赠与人已经完成领用
	cc.from_partid = from_partid;
	//这里需要对couponcode原始数据做擦除
	SimpleAjax account_data = JC.internal.call(SimpleAjax, 'crm', '/h5/p/init_ap_account', [pid:pid,part_id:get_partid]);
	logger.info('init ap info:' + JackSonBeanMapper.toJson(account_data));
	
	def ap_id = null;
	def mobile = null;
	if(account_data&&account_data.available) {
		logger.info('get key info:' + JackSonBeanMapper.toJson(account_data.data));
		ap_id = account_data.data[0];
		mobile = account_data.data[1];
	}
	logger.info('ap_id:' + ap_id + ', mobile=' + mobile);
	code.account_project_id = ap_id;
	code.mobile = mobile;
	JcTemplate.INSTANCE().update(code);
}
JcTemplate.INSTANCE().save(cc);
return SimpleAjax.available();


