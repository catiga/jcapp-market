package com.jeancoder.market.ready.entity

import java.sql.Timestamp
import java.util.Date
import org.apache.commons.io.filefilter.CanReadFileFilter
import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ma_server_coupon_rule")
class ServerCouponRule{
	@JCID
	BigInteger id;
	Date a_time = new Date();//创建时间
	Timestamp c_time;//更新时间
	BigInteger batch_id;//卡券id
	
	String	stores;  //门店
	String	server;	 //服务
	String	rule_value; //服务规则
	String	time_type;	//限制时间类型
	String	time_value;	//限制时间
	String	quantitys_key; //卡券规则
	String	quantitys_value; //卡券数量
}
