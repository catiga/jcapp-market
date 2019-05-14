package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "cbpub_info")
class CbPubInfo {

	@JCID
	BigInteger id;
	
	String title;
	
	String info;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	@JCForeign
	BigInteger pid;
	
	Integer flag = 0;
	
	@JCForeign
	BigInteger ouid;
	
	String ouname;
	
	String ss;
	
	/**
	 * 00** 手机号类型发券
	 * 1*** 智能营销规则发券
	 */
	String cbpt;
}
