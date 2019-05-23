package com.jeancoder.market.entry.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.util.GlobalHolder
import com.jeancoder.market.ready.util.JackSonBeanMapper

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	def list = JC.internal.call('project', '/incall/mystore', [pid:GlobalHolder.proj.id]);
	list = JackSonBeanMapper.jsonToList(list);
	final def dtoList = list;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询门店列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询门店列表失败"));
}
return result;