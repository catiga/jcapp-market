package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService

def code_id = JC.internal.param('code_id');

if(!code_id) {
	return SimpleAjax.notAvailable('param_error,参数错误');
}
CouponCode code = CouponService.INSTANSE.get_codes_by_id(code_id);
if(!code) {
	return SimpleAjax.notAvailable('obj_not_found,券码信息未找到');
}

code.batch = CouponBatchService.INSTANSE.getById(code.batch_id);

return SimpleAjax.available('', code);

