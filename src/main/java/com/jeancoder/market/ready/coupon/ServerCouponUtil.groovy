package com.jeancoder.market.ready.coupon

import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.dto.coupon.CouponCodeDto
import com.jeancoder.market.ready.dto.coupon.CouponRule
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.util.MoneyUtil

class ServerCouponUtil extends CouponUtilAbstract {

	@Override
	public CouponCodeDto getCouponDto(def batch, def couponCode, Object rule) {
		def dto = super.getCouponDto(batch, couponCode, rule);
		def rules  = []
		if (CouponConstants._coupon_type_voucher_.equals(batch.coupon_type)) {
			//代金劵
			String amount = MoneyUtil.get_yuan_from_cent(dto.rule_value);
			amount = MoneyUtil.outEndZero(amount);
			rules.add(amount + "元代金");
		}
		if (CouponConstants._coupon_type_exchange_.equals(batch.coupon_type)) {
			// 兑换劵
			String[] values = dto.rule_value.split("/");
			if (values.length == 3) {
				rules.add(values[0] + "张卡劵兑换" + values[1] + "个服务补"+ MoneyUtil.get_yuan_from_cent(values[2]) + "元")
			}
			
		}
		if (CouponConstants._coupon_type_discount_.equals(batch.coupon_type)) {
			// 折扣劵
			String discount = dto.rule_value;
			discount = MoneyUtil.get_yuan_from_cent(discount);
			rules.add(MoneyUtil.outEndZero(discount) + "折折扣");
		}
		dto.rule = rules;
		return dto;
	}

	@Override
	public CouponRule isOrderAvailable(def batch, def couponCode, def rule, def order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CouponRule consumeCouponPrice(List<CouponCode> couponCodes, BigInteger sid, def order, def totalAmount,def param) {
		// TODO Auto-generated method stub
		return null;
	}

}
