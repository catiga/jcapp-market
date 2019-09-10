package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tp_quesresu")
class QuesResult {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pack_id;
	
	@JCForeign
	BigInteger basic_id;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
