package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try{
	String mt = request.getParameter("mt");
	result.setView("market/add/add_" + mt);
	return result;
}catch(Exception e){
	Logger.error("查询营销列表失败",e);
	return result.setData(AvailabilityStatus.notAvailable("查询营销列表失败"));
}