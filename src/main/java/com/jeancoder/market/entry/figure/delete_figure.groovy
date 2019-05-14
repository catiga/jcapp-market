package com.jeancoder.market.entry.figure

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.service.FigureInfoService

Result result = new Result();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName());
def id = JC.request.param('id')?.trim();
try {
	Integer num = FigureInfoService.INSTANSE.delete_by_id(id);
	if (num>0) {
		result.setData(AvailabilityStatus.available());
		return result;
	}
} catch (Exception e) {
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("删除轮播图失败"));
}