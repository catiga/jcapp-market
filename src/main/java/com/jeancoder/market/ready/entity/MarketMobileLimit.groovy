package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ma_market_limit")
class MarketMobileLimit {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger market_id;
	
	@JCForeign
	BigInteger rule_id;
	
	String mobile;
	
	/*
	 * 正整数有意义，覆盖market表规则对应的设计规则
	 */
	Integer buy_num;	//限制购票的数量
	
	Date a_time;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
}
