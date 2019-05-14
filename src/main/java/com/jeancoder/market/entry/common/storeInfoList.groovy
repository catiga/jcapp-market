package com.jeancoder.market.entry.common

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.sys.StoreInfoDto;
import com.jeancoder.market.ready.util.GlobalHolder

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	List<StoreInfoDto> list = JC.internal.call(StoreInfoDto, 'project', '/incall/mystore', [pid:GlobalHolder.proj.id]);
	final def dtoList = list;
	result.setData(AvailabilityStatus.available(dtoList));
} catch (Exception e) {
	Logger.error("查询门店列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询门店列表失败"));
}
return result;