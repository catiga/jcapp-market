package com.jeancoder.market.ready.entity

import java.sql.Timestamp
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn
import com.jeancoder.jdbc.bean.JCBean

@JCBean(tbname = "ma_order_coupon")
class OrderCoupon {
	
	@JCID
	BigInteger id;  // 
	
	BigInteger pid;  // 
	
	BigInteger order_id;  //  订单id
	
	BigInteger order_item_id;  //  子订单id 
	
	String order_no;  // 订单编号
	
	String offer_amount;  // 折扣金额
	
	String oc;  //  订单类型
	
	BigInteger cbid;  // 卡劵id
	
	String cash_dd;  // 
	
	Timestamp c_time ;
	
	Integer flag = 0;
	
	@JCNotColumn
	String code;
	@JCNotColumn
	String mobile;
}
