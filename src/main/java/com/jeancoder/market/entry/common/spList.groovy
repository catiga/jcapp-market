package com.jeancoder.market.entry.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.util.GlobalHolder

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
JCRequest request = RequestSource.getRequest();
try {
	def list = JC.internal.call(SimpleAjax, "reserve","sp/sp_list" ,[pid:GlobalHolder.proj.id])
	return list;
} catch (Exception e) {
	Logger.error("查询预约服务失败", e);
	return SimpleAjax.notAvailable("查询预约服务失败")
}
