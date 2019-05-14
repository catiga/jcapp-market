package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "mm_market_rule_ticketingsys")
class MarketRuleTicketingsys {
	@JCID
	BigInteger id;
	BigInteger market_info_id;
	BigInteger index_id;
	Integer flag=0;
	BigInteger pid;
	String mc_rule_name;//规则名称
	String desi_movie;//指定影厅
	String spru_time_spec;//时间策略
	String mc_p_streg;//价格策略
	String mc_l_u;//购票数量限制
	String mc_l_u_f;//购票频率限制
	String mc_l_u_v;//具体购票频率值
	Integer is_mc_rule;//是否仅会员参与
	String member_card_rule;//限制会员
	String member_card_rule_name;//限制会员名称
	Date a_time;
	Timestamp c_time;
	String time_type;
	String desi_movie_name;
	String hall_id;
	String store_id;
	String film_no;
}
