package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleRecharge
import com.jeancoder.market.ready.entity.MarketRuleTcss
import com.jeancoder.market.ready.service.MarketInfoServer
import com.jeancoder.market.ready.service.MarketRuleRechargeService
import com.jeancoder.market.ready.service.MarketRuleTcssService
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.RemoteUtil
import java.text.SimpleDateFormat

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName());

try{
	String mtype = request.getParameter("mtype");
	String id = request.getParameter("id");
	String mstatus = request.getParameter("mstatus");
	BigInteger pid=RemoteUtil.getProj().getId();
	MarketInfo mInfo=MarketInfoServer.INSTANSE.getItem(pid, new BigInteger(id));
	if (mInfo == null) {
		MarketInfo info=new MarketInfo();
		MarketRuleRecharge detail=new MarketRuleRecharge();
		result.addObject("mInfo", info);
		result.addObject("detail", detail);
	} else {
		result.addObject("mInfo", mInfo);
		if ("2110".equals(mtype)) {
			BigInteger rid = mInfo.mru_id;
			MarketRuleRecharge detail=MarketRuleRechargeService.INSTANSE.getItem(pid, rid);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format1 = format.format(mInfo.start_time);
			String format2 = format.format(mInfo.end_time);
			result.addObject("start_time", format1);
			result.addObject("end_time", format2);
			result.addObject("detail", detail);
		} else if ("2000".equals(mtype)) {
			MarketRuleTcss detail = MarketRuleTcssService.INSTANSE.getItem(mInfo.id);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format1 = format.format(mInfo.start_time);
			String format2 = format.format(mInfo.end_time);
			result.addObject("start_time", format1);
			result.addObject("end_time", format2);
			result.addObject("detail", detail);

			Logger.info("market detail: {}", JackSonBeanMapper.toJson(detail));
		}
	}

	result.addObject("mstatus", mstatus);
	result.setView("market/edit/edit_" + mtype);
	return result;
}catch(Exception e){
	Logger.error("查询营销列表失败",e);
	return result.setData(AvailabilityStatus.notAvailable("查询营销列表失败"));
}