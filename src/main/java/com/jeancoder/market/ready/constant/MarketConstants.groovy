package com.jeancoder.market.ready.constant

class MarketConstants {
	
	public static final String _market_type_normal_ = "1000";
	public static final String _market_type_tc_ss_ = "2000";
	public static final String _market_type_tc_ss_for_member_card_ = "2200";
	public static final String _market_type_tc_gift_ = "2100";
	public static final String _market_type_mc_recharge_ = "3000";
	public static final String _market_type_recharge_gift_ = "2110";
	
	public static boolean is_sys_supp_market_type(String type_key) {
		boolean ret = false;
		if(type_key.equals(_market_type_normal_)||type_key.equals(_market_type_tc_ss_)
				||type_key.equals(_market_type_tc_gift_)||type_key.equals(_market_type_mc_recharge_)
				||type_key.equals(_market_type_tc_ss_for_member_card_)||type_key.equals(_market_type_recharge_gift_)) {
			ret = true;
		}
		return ret;
	}
	
	public static boolean is_sys_supp_pricestregy_type(String type_key) {
		if(type_key.equals(PriceStregyConstants._price_minus_)||type_key.equals(PriceStregyConstants._price_discount_)||type_key.equals(PriceStregyConstants._price_fixed_)
				||type_key.equals(PriceStregyConstants._price_recharge_return_)||type_key.equals(PriceStregyConstants._reduce_by_low_price_)||type_key.equals(PriceStregyConstants._price_original_)) {
			return true;
		}
		return false;
	}
	
	//活动未开始
	public static final String _market_status_wait_ = "10";
	//活动进行中
	public static final String _market_status_ing_ = "20";
	//活动进行中暂停
	public static final String _market_status_pause_ = "21";
	//活动结束
	public static final String _market_status_finish_ = "30";
	
	public class PriceStregyConstants {
		//立减指定价格
		public static final String _price_minus_ = "00";
		//固定折扣价格
		public static final String _price_discount_ = "10";
		//固定价格销售
		public static final String _price_fixed_ = "20";
		//充值反
		public static final String _price_recharge_return_ = "40";
		//相比最低票价低
		public static final String _reduce_by_low_price_ = "50";
		
		//正价销售
		public static final String _price_original_ = "99";
		
	}
}
