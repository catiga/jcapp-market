package com.jeancoder.market.entry.util.coupon.bymob.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.entity.CbPubItem
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.util.GlobalHolder

def pubid = JC.request.param('pubid');
def qk = JC.request.param('qk');
if(!qk) {
	return SimpleAjax.notAvailable('param_error,请输入要查询的手机号码');
}

def mobiles = qk.split(" ");
def sql = 'select * from CbPubItem where flag!=? and pubid=? and mobile in (';
def params = [];
params.add(-1); params.add(pubid);
mobiles.each({
	if(it) {
		sql += '?,';
		params.add(it);
	}
});

sql = sql.substring(0, sql.length() - 1) + ')';
def pub_items = JcTemplate.INSTANCE().find(CbPubItem, sql, params.toArray());

return SimpleAjax.available('', pub_items);
