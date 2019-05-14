package com.jeancoder.market.entry.coupon.servpp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.ServicePack
import com.jeancoder.market.ready.service.ServicePackService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String sp_no = request.getParameter("sp_no");
	String sp_name = request.getParameter("sp_name");
	BigInteger pid = RemoteUtil.getProj().getId();
	ServicePack sp = new ServicePack();
	sp.spno = sp_no==""?generate_coupon_no(pid):sp_no;
	sp.spname = sp_name;
	sp.pid = pid;
	Integer resultCode = ServicePackService.INSTANSE.saveItem(sp);
	if(resultCode<0){
		return result.setData(AvailabilityStatus.notAvailable("保存失败"));
	}
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("保存服务失败");
	return AvailabilityStatus.notAvailable("保存服务失败"+e.toString());
}
//自动生成编号
String generate_coupon_no(def pid) {
	String buffer = "sv" + pid.toString() + DateUtil.format_yyyyMMdd(new Date()) + this.nextInt(100, 999);
	buffer = buffer.replaceAll("\\-", "");
	return buffer;
}

int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}