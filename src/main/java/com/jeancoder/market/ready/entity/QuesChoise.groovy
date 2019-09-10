package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_queschoise")
class QuesChoise {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger item_id;
	
	String awno;
	
	String awname;
	
	String awvise;
	
	Integer input_falg = 0;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
