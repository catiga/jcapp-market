package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_quesitem")
class QuesItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pack;
	
	String itemno;
	
	String question;
	
	String subhead;
	
	String qt;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	List<QuesChoise> choises;
}
