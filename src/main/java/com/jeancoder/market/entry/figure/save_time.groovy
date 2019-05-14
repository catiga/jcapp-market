package com.jeancoder.market.entry.figure

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.FigureInfo
import com.jeancoder.market.ready.service.FigureInfoService
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName());
def id = JC.request.param('id')?.trim();
def start_time = JC.request.param('start_time')?.trim();
def end_time = JC.request.param('end_time')?.trim();
try {
	if (StringUtil.isEmpty(start_time)) {
		return result.setData(AvailabilityStatus.notAvailable("开始时间不能为空"));
	}
	if (StringUtil.isEmpty(end_time)) {
		return result.setData(AvailabilityStatus.notAvailable("结束时间不能为空"));
	}
	FigureInfo info = new FigureInfo();
	info.id = new BigInteger(id);
	info.start_time = start_time;
	info.end_time = end_time;
	Integer nums = FigureInfoService.INSTANSE.update_time(info);
	if(nums>0){
		result.setData(AvailabilityStatus.available());
		return result;
	}
} catch (Exception e) {
	logger.error("",e.toString());
	return result.setData(AvailabilityStatus.notAvailable("保存时间失败"));
}