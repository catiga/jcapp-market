package com.jeancoder.market.ready.dto

class MarketJsonRule extends AbstractMarketRuleDto{

	private List<KV> l_cs;
	
	private List<KV> l_cts;
	
	private List<KV> l_pts;
	
	private List<KV> l_ht;
	
	private KV cal_sw;
	
	private String cal_a;
	private String cal_r;
	
	private String l_plays;//放映时间段开始
	private String l_playe;//放映时间段结束
	
	private String l_pds;//放映开始日期
	private String l_pde;//放映结束日期
	
	private KV l_pay;//是否限制支付账户
	
	private String l_cwro = "0";
	
	//new field 20161204
	//默认非最低票价结算
	private Integer low_price_settle = 0;
	
	//默认收取手续费
	private Integer clear_handle_fee_policy = 0;
}
