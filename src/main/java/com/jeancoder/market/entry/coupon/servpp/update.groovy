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
	String sp_id = request.getParameter("sp_id");
	String sp_no = request.getParameter("sp_no");
	String sp_name = request.getParameter("sp_name");
	BigInteger pid = RemoteUtil.getProj().getId();
	ServicePack sp = new ServicePack();
	sp.id = new BigInteger(sp_id);
	sp.spno = sp_no;
	sp.spname = sp_name;
	sp.pid = pid;
	Integer resultCode = ServicePackService.INSTANSE.updateItem(sp);
	if(resultCode<=0){
		return result.setData(AvailabilityStatus.notAvailable("更新失败"));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("更新服务失败");
	return AvailabilityStatus.notAvailable("更新服务失败"+e.toString());
}
