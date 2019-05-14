package com.jeancoder.market.ready.coupon

import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.dto.coupon.CouponCodeDto
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.util.DateUtil

abstract class CouponUtilAbstract implements CouponInterface {

	@Override
	def getCouponDto(def batch, def couponCode, def rule) {
		CouponCodeDto dto = new CouponCodeDto();
		dto.id = couponCode.id;
		dto.code = couponCode.code
		dto.batch_title =  batch.title;
		dto.coupon_type = batch.coupon_type;
		dto.crapp = batch.crapp;
		dto.validate_type = couponCode.validate_type;
		dto.rule_value = rule.rule_value;
		if (CouponConstants._coupon_validate_type_forever_.equals(couponCode.validate_type)) {
			dto.validate_end = 0;
			// 永久有效
		} else if (CouponConstants._coupon_validate_type_unidied_.equals(couponCode.validate_type)) {
			// 固定日期有效
			Date validateDate =  DateUtil.getDate(couponCode.validate_period, "yyyy-MM-dd HH:mm:ss");
			if (validateDate != null) {
				dto.validate_end = validateDate.getTime();
			}
		} else if (CouponConstants._coupon_validate_type_days_.equals(couponCode.validate_type)) {
			// 按领用天数失效
			Long getTime = couponCode.get_time.getTime();
			dto.validate_end = getTime + (new  Long(couponCode.validate_period))*24*60*60*1000;
		}
		return dto;
	}

	@Override
	 def isAvailable(def batch, def couponCode, def rule) {
		if (CouponConstants._coupon_validate_type_forever_.equals(couponCode.validate_type)) {
			return true;
			// 永久有效
		} else if (CouponConstants._coupon_validate_type_unidied_.equals(couponCode.validate_type)) {
			// 固定日期有效
			Date validateDate =  DateUtil.getDate(couponCode.validate_period,"yyyy-MM-dd HH:mm:ss");
			Date currentDate=DateUtil.getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			if (validateDate != null) {
				int i=currentDate.compareTo(validateDate);
				if(i<=0){
					return true;
				}else{
					return false;
				}
			}
			return false;
		} else if (CouponConstants._coupon_validate_type_days_.equals(couponCode.validate_type)) {
			// 按领用天数失效
			Long getTime = couponCode.get_time.getTime();
			Long validate_end = getTime + (new  Long(couponCode.validate_period))*24*60*60*1000;
			if(getTime <= validate_end){
				return true;
			}
			return false;
		}
		return false;
	}
}
