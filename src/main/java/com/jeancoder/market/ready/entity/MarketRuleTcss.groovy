package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'mm_market_rule_tcss')
class MarketRuleTcss {

	@JCID
	BigInteger id;
	
	BigInteger market_id;
	
	BigInteger store_id;		//按影城来生成多条
	
	String store_name;			//缓存影城名称
	
	String limit_movies;		//限制的影片信息
	
	String limit_halls;			//限制的影厅信息
	
	String limit_pay_types;		//限制的支付方式
	
	String plan_start_date;		//限制的放映开始日期
	
	String plan_start_time;		//限制的放映开始时间
	
	String plan_end_date;		//限制的放映结束日期
	
	String plan_end_time;		//限制的放映结束时间
	
	String price_policy;		//价格策略
	
	String number_policy;		//数量策略
	
	Integer bind_pay_account = 0;	//是否需要绑定支付账户，默认不需要
	
	Integer first_buy_eff = 0;	//是否只有首单有效，默认不是
	
	Integer low_price_settle = 0;	//是否最低票价结算
	
	BigDecimal settle_price = null;	//如果非最低票价结算，需要填入结算金额
	
	String settle_obj;			//结算对象，记录文字
	
	BigDecimal handle_fee;		//独立活动手续费
	
	/**
	 * 10:锁坐占名额
	 * 20:出票占名额
	 */
	String join_type = '10';	//参与方式
	
	Date a_time;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
}
