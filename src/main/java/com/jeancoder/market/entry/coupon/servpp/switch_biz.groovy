package com.jeancoder.market.entry.coupon.servpp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.ServicePack
import com.jeancoder.market.ready.service.ServicePackService
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String sp_id = request.getParameter("spid");
	BigInteger pid = RemoteUtil.getProj().getId();
	BigInteger id = new BigInteger(sp_id);
	ServicePack spack = ServicePackService.INSTANSE.getServicePackBy_id(id)
	if("".equals(spack)){
		return result.setData(AvailabilityStatus.notAvailable("不存在这个服务"));
	}
	
	Integer resultCode = ServicePackService.INSTANSE.switch_biz(spack);
	if(resultCode<=0){
		return result.setData(AvailabilityStatus.notAvailable("更新失败"));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("状态设置失败");
	return AvailabilityStatus.notAvailable("状态设置失败");
}
