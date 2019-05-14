package com.jeancoder.market.entry.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
BigInteger pid = RemoteUtil.getProj().getId();
CouponService cs = CouponService.INSTANSE;
try{
	String id_s = request.getParameter("id_s");
	if(id_s==null){
		return result.setData(AvailabilityStatus.notAvailable("请选择要作废的卡券"));
	}
	String [] id_string = id_s.split(",");
	for(int i=0;i<id_string.length;i++){
		BigInteger id = new BigInteger(id_string[i]);
		CouponCode code = CouponService.INSTANSE.get_codes_by_id(id);
		if(code.status== CouponConstants._coupon_code_status_available_ ||code.status==CouponConstants._coupon_code_status_touse_){
			code.status=CouponConstants._coupon_code_status_disable_;
			cs.update_code(code);
		}else{
			return result.setData(AvailabilityStatus.notAvailable("卡券"+id+"不可以作废,请重新选择"));;
		}
	}
	result.setData(AvailabilityStatus.available());
	return result;	
}catch(Exception e){
	logger.error("卡券作废失败",e);
	return result.setData(AvailabilityStatus.notAvailable("卡券作废失败"));
}
