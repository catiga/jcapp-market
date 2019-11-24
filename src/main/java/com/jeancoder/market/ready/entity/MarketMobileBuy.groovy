package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'ma_market_mobile_buy')
class MarketMobileBuy {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger market_id;
	
	BigInteger market_rule_id;
	
	String mobile;
	
	Integer buy_num;
	
	Date a_time;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
	
	String order_no;
	
	String oc;
	
}
