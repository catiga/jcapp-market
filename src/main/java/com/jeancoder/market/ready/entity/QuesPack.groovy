package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname = "data_tp_quespack")
class QuesPack {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String pack_no;
	
	String pack_name;
	
	String pack_info;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Integer useflag = 1;
	
	Integer sgutimes = 0;
	
	String tplname;
	
	@JCNotColumn
	List<QuesItem> items;
}
