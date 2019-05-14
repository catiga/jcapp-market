package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.google.gson.annotations.Until
import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "ma_coupon_code")
public class CouponCode {
	
	@JCID
	BigInteger id;  // id
	BigInteger batch_id;
	String code;
	
	String status;
	
	String mobile;
	
	String source;
	
	String takeby;
	
	Long account_project_id;
	
	Date get_time;
	
	Timestamp c_time ;
	Date a_time;
	Integer flag = 0;
	Date use_time;
	
	/** 20180319 add new field **/
	/**
	 * 1:用户使用
	 * 2:后台验证
	 */
	Integer use_type = 1;
	
	BigInteger use_admin_id;
	String use_admin_name;
	
	String validate_type;
	String validate_period;
	
	@JCNotColumn
	CouponBatch  batch 
	
}
