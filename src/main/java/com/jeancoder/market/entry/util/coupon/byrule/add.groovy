package com.jeancoder.market.entry.util.coupon.byrule

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.entity.CbPubItem
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.util.GlobalHolder

def pubid = JC.request.param('pubid');

def sql = 'select * from CouponBatch where flag!=? and cbstatus=? and pid=?';
def cbs = JcTemplate.INSTANCE().find(CouponBatch, sql, -1, CouponConstants._coupon_batch_enable_, GlobalHolder.proj.id);

Result result = new Result();

def pub = null;
if(pubid&&pubid!='0') {
	pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=?', pubid);
	if(pub) {
		pubid = pub.id;
	} else {
		pub = new CbPubInfo();
	}
} else {
	pub = new CbPubInfo();
}
result.addObject('pubid', pubid);
result.addObject('pub', pub);

//获取设置的已发券明细
sql = 'select * from CbPubItem where flag!=? and pubid=? order by mobile';
def pub_items = JcTemplate.INSTANCE().find(CbPubItem, sql, -1, pubid);
result.addObject('pub_items', pub_items);

result.addObject('available_cbs', cbs);
result.setView("util/coupon/byrule/add");
return result;

