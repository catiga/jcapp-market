package com.jeancoder.market.entry.coupon.destroy

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
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	def  code = request.getParameter("code");
	def pid = RemoteUtil.proj.id;
	if(StringUtil.isEmpty(code)) {
		return AvailabilityStatus.notAvailable("卡劵为空");
	}
	List<CouponCode> batch_ = CouponService.INSTANSE.get_codes_by_ids(code,pid);
	if(batch_==null||batch_.isEmpty()) {
		return AvailabilityStatus.notAvailable("卡劵为空");
	}
	for(CouponCode cb : batch_) {
		if(cb.status.equals(CouponConstants._coupon_code_status_touse_)) {
			cb.status = CouponConstants._coupon_code_status_used_;
			cb.use_time = new Date();
			CouponService.INSTANSE.update_code(cb);
		}
	}
	return AvailabilityStatus.available();
}catch(Exception e){
	Logger.error("验证失败", e);
	return AvailabilityStatus.notAvailable("验证失败");
}


 



