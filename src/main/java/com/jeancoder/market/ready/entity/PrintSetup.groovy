package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "sys_project_print_setup")
class PrintSetup {

	@JCID
	BigInteger id;
	
	String setup_type;
	
	@JCForeign
	BigInteger proj_id;
	
	String setup_json;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
