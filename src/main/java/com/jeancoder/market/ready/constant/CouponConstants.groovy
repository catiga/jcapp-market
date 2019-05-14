package com.jeancoder.market.ready.constant

class CouponConstants {
	public static final String _coupon_app_general_ = "1000";
	public static final String _coupon_app_ticket_ = "2000";
	public static final String _coupon_app_appoint_ = "5000";
	public static final String _coupon_online_server_ = "5001";

	// 代金使用
	public static final String _coupon_type_voucher_ = "1000";
	// 兑换使用
	public static final String _coupon_type_exchange_ = "2000";
	// 折扣券
	public static final String _coupon_type_discount_ = "3000";
	
	//有效期常量
	public static final String _coupon_validate_type_forever_ = "0000"; // 永久有效
	public static final String _coupon_validate_type_unidied_ = "0010";// 统一时间失效
	public static final String _coupon_validate_type_days_ = "1001";// 固定领用天数 失效
	//public static final String _coupon_validate_type_months_ = "1002";
	//public static final String _coupon_validate_type_years_ = "1003";
	
	//使用金额常量
	public static final String _coupon_use_con_money_unlimit_ = "1000";
	public static final String _coupon_use_con_money_full_to_off_ = "1010";
	
	//使用商品常量100
	public static final String _coupon_use_con_gs_unlimit_ = "2000";
	public static final String _coupon_use_con_gs_aim_goods_ = "2010";
	public static final String _coupon_use_con_gs_aim_goods_cats_ = "2020";
	
	//使用门店常量
	public static final String _coupon_use_con_ss_unlimit_ = "3000";
	public static final String _coupon_use_con_ss_aim_ = "3010";
	
	//使用影片类型常量
	public static final String _coupon_use_con_ft_unlimit_ = "4000";
	public static final String _coupon_use_con_ft_aim_ = "4010";

	// 00:待分配;10:已分配;20:已使用 ;50:作废
	public static final String _coupon_code_status_available_ = "00";
	public static final String _coupon_code_status_touse_ = "10";
	public static final String _coupon_code_status_used_ = "20";
	public static final String _coupon_code_status_expired_ = "30";
	public static final String _coupon_code_status_locked_ = "40";
	public static final String _coupon_code_status_disable_ = "50";
	
	// 现在持有人的获得方式
	public static final String _coupon_code_source_send_ = "10";//后台发放
	public static final String _coupon_code_source_given_ = "20";//好友转赠
	public static final String _coupon_code_source_get_ = "30";//用户自助获取
	public static final String _coupon_code_source_activate_ = "40";//用户通过券码激活

	
	// 卡劵批次状态
	public static final String _coupon_batch_enable_ = "20";//启用状态
	public static final String _coupon_batch_stop_ = "21";//停用
	public static final String _coupon_batch_init_ = "10";//初始化
	
	public static final String goods_type_code = "100";
	
	
	/**
	 * 卡劵数量策略不设置
	 */
	public static final String  _QUANTITYS_TYPE_NO_ = "100";
	/**
	 * 满多少元  可用使用
	 */
	public static final String _QUANTITYS_TYPE_FULL_ = "101";
	
	/**
	 * 每满多少元
	 */
	public static final String _QUANTITYS_TYPE_EVERY_FULL_ = "102";
	
	/**
	 * 每笔 交易 换多少张
	 */
	public static final String _QUANTITYS_TYPE_EVERY_PEN_ = "103";
	
	/**
	 * 每张电影票可以兑换多少张卡劵
	 */
	public static final String _QUANTITYS_TYPE_EVERYS_ONE_ = "104";
	
	/**
	 * 按周分时
	 */
	public static final String _TIME_WEEK_SHARING_ = "w";
	
	/**
	 * 按日前分时
	 */
	public static final String _TIME_DAY_SHARING_ = "d";
	
	/**
	 * 全天有效
	 */
	public static final String _TIME_NO_SHARING_ = "0";
	
	/**
	 * 保护最低价
	 */
	public static final String _P_LOWEST_YES = "1";
	
	/**
	 * 不保护最低价
	 */
	public static final String _P_LOWEST_NOT = "2";

	public static boolean is_valid_status(String status) {
		if (status == null) {
			return false;
		}
		if (status.equals(_coupon_code_status_available_)
				|| status.equals(_coupon_code_status_touse_)
				|| status.equals(_coupon_code_status_used_)) {
			return true;
		}
		return false;
	}
	
	public static boolean is_valid_coupon_type(String type) {
		boolean is_valid = false;
		if(type.equals(_coupon_type_exchange_)||type.equals(_coupon_type_voucher_)||type.equals(_coupon_type_discount_)) {
			is_valid = true;
		}
		return is_valid;
	}
	
	public static boolean is_valid_coupon_validate_type(String validate_type) {
		boolean is_valid = false;
		if(validate_type.equals(_coupon_validate_type_days_)||
				validate_type.equals(_coupon_validate_type_forever_)
//				||validate_type.equals(_coupon_validate_type_months_)||
				||validate_type.equals(_coupon_validate_type_unidied_)
//				validate_type.equals(_coupon_validate_type_years_)
				) {
			is_valid = true;
		}
		return is_valid;
	}
}
