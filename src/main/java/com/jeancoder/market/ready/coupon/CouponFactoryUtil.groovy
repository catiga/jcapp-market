package com.jeancoder.market.ready.coupon

import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.OnlineServerCouponRule

class CouponFactoryUtil {
	
	public static def getCouponUtil(String crapp) {
		if (CouponConstants._coupon_app_general_.equals(crapp)) {
			return new GoodsCouponUtil();
		}
		if (CouponConstants._coupon_app_ticket_.equals(crapp)) {
			return new MovieCouponUtil();
		}
		if (CouponConstants._coupon_app_appoint_.equals(crapp)) {
			return new ServerCouponUtil();
		}
		if (CouponConstants._coupon_online_server_.equals(crapp)) {
			return new OnlineServerCouponUtil();
		}
		return null;
	}
	
	static def getCouponCodeRule () {
		
	}
}
