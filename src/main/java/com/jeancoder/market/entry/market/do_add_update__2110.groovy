package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketJsonRuleWithMC
import com.jeancoder.market.ready.service.MarketRuleService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil

//@RequestMapping(value = "_2110/do_add_update", method = RequestMethod.POST)
//public @ResponseBody AvailabilityStatus _2110_do_add_update(
//		@RequestParam(value = "mc_id", required = false) Long mc_id,
//		@RequestParam(value = "mc_type") String mc_type,
//		@RequestParam(value = "mc_name") String mc_name,
//		@RequestParam(value = "mc_no", required = false) String mc_no,
//		@RequestParam(value = "mc_info", required = false) String mc_info,
//		@RequestParam(value = "mc_start_time") String mc_start_time,
//		@RequestParam(value = "mc_end_time") String mc_end_time,
//		@RequestParam(value = "mc_p_streg") String mc_p_streg,
//		@RequestParam(value = "mc_l_ms", required = false) String mc_l_ms,
//		@RequestParam(value = "mc_l_ms_name", required = false) String mc_l_ms_name,
//		@RequestParam(value = "mc_content", required = false) String mc_content
//	) {
//	SysProjectInfo project = this.getSysProj();
//	MarketInfo market = null;
//	if(mc_id!=null&&mc_id>0l) {
//		market = marketService.get_market_by_id(mc_id);
//	}
//	if(market==null) {
//		market = new MarketInfo();
//		market.setProject(project);
//		market.setaTime(new Date());
//		market.setCg(0);
//		market.setMstatus("10");
//		market.setCgu(this.getAdminUser().getId());
//		mc_no = StringUtil.trim(mc_no);
//		if(StringUtil.isEmpty(mc_no)) {
//			mc_no = CPISCoderTools.generateCouponNum();
//		}
//		market.setDnum(mc_no);
//	} else {
//		if(!market.getProject().getId().equals(project.getId())) {
//			return AvailabilityStatus.notAvailable(JsConstants.object_belong_error);
//		}
//	}
//	Date start_time = null, end_time = null;
//	try {
//		start_time = DateUtil.parse_yyyyMMddHHmmss(mc_start_time);
//		end_time = DateUtil.parse_yyyyMMddHHmmss(mc_end_time);
//		if(start_time.after(end_time)) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//		if(start_time==null||end_time==null) {
//			return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//		}
//	} catch(Exception e) {
//		log.error("", e);
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	
//	//开始校验各种数据
//	MarketRechargeGiftJsonRule m_j_r = null;
//	try {
//		m_j_r = new MarketRechargeGiftJsonRule();
//		
//		//校验价格策略
//		List<KV> price_stregy = MarketUtil.build_recharge_gift_stregy(mc_p_streg);
//		m_j_r.setPs(price_stregy);
//		
//		//设置手续费策略
////			m_j_r.setClear_handle_fee_policy(clear_handle_fee_policy);
////
////			if(mc_l_cs!=null&&!mc_l_cs.equals("")) {
////				List<KV> kvs = MarketUtil.build_kvs(mc_l_cs, mc_l_cs_name);
////				if(kvs!=null)
////					m_j_r.setL_cs(kvs);
////			}
//		if(mc_l_ms!=null&&!mc_l_ms.equals("")) {
//			List<KV> kvs = MarketUtil.build_kvs(mc_l_ms, mc_l_ms_name);
//			m_j_r.setL_ms(kvs);
//		}
////			if(mc_l_cts!=null&&!"".equals(mc_l_cts)){
////				List<KV> kvs = MarketUtil.build_kvs(mc_l_cts, mc_l_cts_name);
////				m_j_r.setL_cts(kvs);
////			}
////			if(mc_l_ht!=null&&!"".equals(mc_l_ht)){
////				List<KV> kvs = MarketUtil.build_kvs(mc_l_ht, mc_l_ht_name);
////				m_j_r.setL_ht(kvs);
////			}
//		
//	} catch(UnvalidValueException uvex) {
//		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
//	}
//	market.setcTime(DataUtils.getCurrentTimestamp());
//	market.setEndTime(end_time);
//	market.setFlag(0);
//	market.setInfo(mc_info);
//	market.setMtype(mc_type);
//	market.setStartTime(start_time);
//	market.setTitle(mc_name);
//	market.setSelectmode(0);
//	market.setContent(mc_content);
//	MarketRule m_r = market.getRule();
//	if(m_r==null) {
//		m_r = new MarketRule();
//		m_r.setMarket(market);
//	}
//	m_r.setJsonRule(JackSonBeanMapper.toJson(m_j_r));
//	try {
//		marketService.do_save_update_market(market, m_r);
//		return AvailabilityStatus.available();
//	} catch(Exception e) {
//		log.error("", e);
//		return AvailabilityStatus.notAvailable(JsConstants.unknown);
//	}
//}