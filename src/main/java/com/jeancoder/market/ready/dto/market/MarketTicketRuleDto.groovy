package com.jeancoder.market.ready.dto.market


class MarketTicketRuleDto {
	String id;
	String market_info_id;
	String mc_rule_name;//规则名称
	String desi_movie;//指定影片
	String spru_time_spec;//时间策略
	String mc_p_streg;//价格策略
	String mc_l_u;//购票数量限制
	String mc_l_u_f;//购票频率限制
	String mc_l_u_v;//具体购票频率值
	String is_mc_rule;//是否仅会员参与
	String member_card_rule;//限制会员
	String time_type;
	String hall_id;
	String store_id;
	String film_no;
}
