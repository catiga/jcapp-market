package com.jeancoder.market.entry.coupon.batch.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode

def batch_id = JC.request.param('batch_id');

//获取对应的所有codes
List<CouponCode> codes = JcTemplate.INSTANCE().find(CouponCode, 'select * from CouponCode where flag!=? and batch_id=? and status=?', -1, batch_id, '00');	//只打印待分配的券

if(codes==null || codes.empty) {
	return SimpleAjax.notAvailable('result_set_empty,可用券码为空');
}

for(x in codes) {
	x.batch = JcTemplate.INSTANCE().get(CouponBatch, 'select * from CouponBatch where id=?', x.batch_id);
}

return SimpleAjax.available('', codes);
