package com.jeancoder.market.entry.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.ServicePack
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.ServicePackService
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String batch_id = request.getParameter("b_id");
	BigInteger pid = RemoteUtil.getProj().getId();
	BigInteger id = new BigInteger(batch_id);
	Integer cbnums = CouponBatchService.INSTANSE.switch_s(id);
	if(cbnums<0){
		return result.setData(AvailabilityStatus.notAvailable("启用或者停用优惠券失败"));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("启用或者停用优惠券失败");
	return AvailabilityStatus.notAvailable("启用或者停用优惠券失败");
}