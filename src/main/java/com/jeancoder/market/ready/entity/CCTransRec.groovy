package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ma_coupon_trans")
class CCTransRec {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger cb_id;
	
	@JCForeign
	BigInteger cc_id;
	
	String cc_code;
	
	String get_partid;
	
	String from_partid;
	
	Timestamp a_time;
	
	Integer flag = 0;
}
