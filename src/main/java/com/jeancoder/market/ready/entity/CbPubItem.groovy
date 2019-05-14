package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "cbpub_item")
class CbPubItem {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pubid;
	
	String mobile;
	
	@JCForeign
	BigInteger cb;
	
	Integer num;
	
	String ccid;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
}
