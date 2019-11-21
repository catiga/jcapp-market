
package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.dto.MovieRuleRechargeDto
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleRecharge
import com.jeancoder.market.ready.entity.MarketRuleTcss
import com.jeancoder.market.ready.service.MarketInfoServer
import com.jeancoder.market.ready.service.MarketRuleRechargeService
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil
import java.text.SimpleDateFormat

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	Long pid = RemoteUtil.getProj().getId();
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String mstatus=request.getParameter("status");

	//分页查询处理
	if(StringUtil.isEmpty(pn_s)){
		pn_s = "1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="10";
	}

	Integer pn = Integer.parseInt(pn_s);
	if(pn<1){
		pn=1;
	}

	Integer ps = Integer.parseInt(ps_s);

	JcPage<MarketInfo> page = new JcPage<MarketInfo>();
	page.setPn(pn);
	page.setPs(ps);

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//返回结果
	page = MarketInfoServer.INSTANSE.getList(pid, page,mstatus);
	for (def a: page.result) {
		a.mc_start_time = format.format(a.start_time);
		a.mc_end_time = format.format(a.end_time);
		if(a.mtype==MarketConstants._market_type_tc_ss_) {
			//取在线选座的规则
			List<MarketRuleTcss> tcss_rules = JcTemplate.INSTANCE().find(MarketRuleTcss, 'select * from MarketRuleTcss where flag!=? and market_id=?', -1, a.id);
			
		} else if(a.mtype==MarketConstants._market_type_recharge_gift_) {
			if(a.mru_id!=null) {
				a.rule= MarketRuleRechargeService.INSTANSE.getItem(pid, a.mru_id);
				String [] mc_p_streg=a.rule.mc_p_streg.split("/")
				List<MovieRuleRechargeDto> recharge=new ArrayList();
				for (int i = 0; i < mc_p_streg.length; i++) {
					MovieRuleRechargeDto mcdto = new MovieRuleRechargeDto();
					String streg=mc_p_streg[i];
					String [] sum=streg.split(",");
					mcdto.mc_recharge=sum[0];
					mcdto.mc_recharge_mold=sum[1];
					mcdto.mc_recharge_number=sum[2];
					mcdto.mc_recharge_amount=sum[3];
					recharge.add(mcdto);
				}
				a.resultList=recharge;
			}
		}
	}
	result.addObject("page", page);
	result.addObject("mstatus", mstatus)
	result.setView("market/index");
	return result;
}catch(Exception e){
	Logger.error("查询营销列表失败",e);
	return result.setData("error_msg","查询营销列表失败");
}
