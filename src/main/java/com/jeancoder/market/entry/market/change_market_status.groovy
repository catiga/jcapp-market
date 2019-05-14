package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.service.MarketInfoServer
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result=new Result();
JCRequest request =RequestSource.getRequest();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName())
try {
	String rid=request.getParameter("id");
	String mstatus=request.getParameter("mstatus");
	def pid = RemoteUtil.getProj().getId();
	if (StringUtil.isEmpty(rid)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	MarketInfo market=new MarketInfo();
	market.id=new BigInteger(rid.toString())
	market.mstatus=mstatus;
	String resultStr = MarketInfoServer.INSTANSE.updateStatus(pid,market.id,market.mstatus);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
} catch (Exception e) {
	logger.error("更改状态失败", e);
	return AvailabilityStatus.notAvailable("更改状态失败");
}