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
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
BigInteger pid = RemoteUtil.getProj().getId();
CouponService cs = CouponService.INSTANSE;
try{
	String id_s = request.getParameter("id_s");
	String validate_period = request.getParameter("validate_period");
	if(id_s==null){
		return result.setData(AvailabilityStatus.notAvailable("请选择要延期的卡券"));
	}
	if(validate_period==null){
		return result.setData(AvailabilityStatus.notAvailable("请输入要修改的时间"));
	}
	String [] id_string = id_s.split(",");
	for(int i=0;i<id_string.length;i++){
		BigInteger id = new BigInteger(id_string[i]);
		CouponCode code = cs.get_codes_by_id(id);
		if(code.status== CouponConstants._coupon_code_status_available_ ||code.status==CouponConstants._coupon_code_status_touse_){
			String validate_type = code.validate_type;
			String validate_period_old;
			if(validate_type=="0000"){
				return result.setData(AvailabilityStatus.notAvailable("永久有效不可以更改"));
			}else if(validate_type=="1001"){
				validate_period_old = code.validate_period;
				if(new BigInteger(validate_period)<new BigInteger(validate_period_old)){
					return result.setData(AvailabilityStatus.notAvailable("要修改的天数必须大于已有天数"));
				}
				code.validate_period=validate_period;
				cs.update_code(code);
			}else if(validate_type=="0010"){
				Date validate_period_new = DateUtil.getDate(validate_period,"yyyy-MM-dd HH:mm:ss");
				Date validate_period_old_date = DateUtil.getDate(code.validate_period,"yyyy-MM-dd HH:mm:ss");
				if(validate_period_new.after(validate_period_old_date)){
					code.validate_period=validate_period;
					cs.update_code(code);
				}
			}
		}else{
			return result.setData(AvailabilityStatus.notAvailable("卡券"+id+"不可以延期,请重新选择"));
		}
	}
	result.setData(AvailabilityStatus.available());
	return result;
}catch(Exception e){
	logger.error("卡券延期失败",e);
	return result.setData(AvailabilityStatus.notAvailable("卡券延期失败"));
}
