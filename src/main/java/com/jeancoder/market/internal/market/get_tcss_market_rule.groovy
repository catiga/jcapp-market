package com.jeancoder.market.internal.market

import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.market.ready.entity.MarketRuleTcss
import java.text.SimpleDateFormat
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.dto.market.MarketInfoDto
import com.jeancoder.market.ready.dto.market.MarketTicketRuleDto
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.service.MarketRuleService
import com.jeancoder.market.ready.util.StringUtil

JCLogger logger = JCLoggerFactory.getLogger(this.getClass().getName());
try {
	String pid = CommunicationSource.getParameter("pid");
	if (StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable("参数不能为空");
	}
	List<MarketInfo> info = MarketRuleService.INSTANSE.get_all_market_info(new BigInteger(pid));
	if (info==null) {
		return SimpleAjax.notAvailable("无可用活动规则");
	}
	List<MarketInfoDto> list_dto = new ArrayList();
	for(MarketInfo item:info){
		MarketInfoDto list = new MarketInfoDto();
		list.id = item.id.toString();
		list.title = item.title;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		list.start_time = sdf.format(item.start_time);
		list.end_time = sdf.format(item.end_time);
		List<MarketTicketRuleDto> dto =new ArrayList();
		List<MarketRuleTcss> ticket = MarketRuleService.INSTANSE.getTcssItemByInfo_id(new BigInteger(pid), item.id);
		for (MarketRuleTcss ticket1:ticket) {
			MarketTicketRuleDto dto1 = new MarketTicketRuleDto();
			dto1.id = ticket1.id.toString();
			dto1.market_info_id = ticket1.market_id.toString();
			dto1.mc_rule_name = item.title;
			dto1.desi_movie = ticket1.limit_movies;
			dto1.spru_time_spec = ticket1.spru_time_spec;
			dto1.mc_p_streg = ticket1.price_policy;
			dto1.mc_l_u = ticket1.number_policy;
			dto1.mc_l_u_f = ticket1.mc_l_u_f;
			dto1.mc_l_u_v = ticket1.mc_l_u_v;
//			dto1.is_mc_rule = String.valueOf(ticket1.is_mc_rule);
//			dto1.member_card_rule = ticket1.member_card_rule;
			dto1.time_type = ticket1.time_type;
			dto1.hall_id = ticket1.hall_id;
			dto1.store_id = ticket1.store_id;
			dto1.film_no = ticket1.film_no;
			dto.add(dto1)
		}
		list.market = dto;
		list_dto.add(list);
	}
	return SimpleAjax.available('',list_dto);
} catch (Exception e) {
	logger.error("查询影票规则列表失败", e);
	return SimpleAjax.notAvailable("查询影票规则列表失败");
}
