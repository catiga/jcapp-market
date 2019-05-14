package com.jeancoder.market.ready.dto

public enum CouponDic {
	
	COUPON_GENERAL("1000", "普通商品类"),
	COUPON_TICKETS("2000", "在线选座类"),
	COUPON_APPOINT("5000", "服务项目类"),
	COUPON_ONLINE_SERVER("5001", "服务预约类");
	
	
	private String name;
	private String code;
	
	private CouponDic(String code, String name) {
		this.name = name;
		this.code = code;
	}

	public String key() {
		return this.code;
	}
	public SelectModel toSlectModel() {
		SelectModel sm = new SelectModel();
		sm.key = this.code;
		sm.name = this.name;
		return sm;
	}

}