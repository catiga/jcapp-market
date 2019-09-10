package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "data_tp_quesresuitem")
class QuesResuItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pack_id;
	
	@JCForeign
	BigInteger basic_id;
	
	@JCForeign
	BigInteger item_id;
	
	@JCForeign
	BigInteger resu_id;
	
	String result;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	@JCNotColumn
	String question;
	
	@JCNotColumn
	String qt;
	
}
