package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "mm_market_rule_recharge")
class MarketRuleRecharge {
	@JCID
	BigInteger id;
	String mc_name;
	BigInteger pid;
	String mc_p_streg;
	String mc_l_ms
	Date a_time;
	String store;
	String mc_l_ms_name;
	String store_name;
	Timestamp c_time;
}
