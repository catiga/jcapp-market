package com.jeancoder.market.entry.coupon.destroy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.dto.CouponDic
import com.jeancoder.market.ready.dto.SelectModel
import com.jeancoder.jdbc.JcPage
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	return result.setView("coupon/destroy/index");
}catch(Exception e){
	Logger.error("查询不到卡批次");
	return result.setData(AvailabilityStatus.notAvailable("查询卡批次失败"));
}
