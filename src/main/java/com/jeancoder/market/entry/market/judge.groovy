package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.constant.MarketConstants



Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
result.setView("market/judge");
try{
List<String> support_mtypes = new ArrayList<String>();
//support_mtypes.add(MarketConstants._market_type_tc_ss_);
support_mtypes.add(MarketConstants._market_type_recharge_gift_);
//if(project.isCinemaProject()) {
//	support_mtypes.add(MarketConstants._market_type_tc_ss_);
//	support_mtypes.add(MarketConstants._market_type_tc_gift_);
//	support_mtypes.add(MarketConstants._market_type_tc_ss_for_member_card_);
//	support_mtypes.add(MarketConstants._market_type_recharge_gift_);
//}
result.addObject("support_mtypes", support_mtypes)
return result;
}catch(Exception e){
	Logger.error("查询营销列表失败",e);
	return result.setData("error_msg","查询营销列表失败");
}
