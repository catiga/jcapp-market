package com.jeancoder.market.internal.util

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

def pubid = JC.internal.param('pubid');

def pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and flag!=?', pubid, -1);

def sql = 'select * from CbPubItem where flag!=? and pubid=? and ccid is null';
List<CbPubItem> result = JcTemplate.INSTANCE().find(CbPubItem, sql, -1, pubid);

for(x in result) {
	
	def mobile = x.mobile;
	def pid = pub.pid;
	def num = x.num;
	
	def mobiles = [];
	while(num>0) {
		mobiles.add(mobile);
		num--;
	}
	logger.info(x.mobile + ": pub batch=" + x.cb + ": pub num=" + x.num + ": MOBILE ARRAY=" + mobiles.size());
	try {
		def pub_ids = CouponService.INSTANSE.give_out_coupons(x.cb, mobiles, pid);
		Collections.sort(pub_ids);
		x.ccid = JackSonBeanMapper.listToJson(pub_ids);
		JcTemplate.INSTANCE().update(x);
	} catch(any) {
		any.printStackTrace();
	}
}

return SimpleAjax.available();
