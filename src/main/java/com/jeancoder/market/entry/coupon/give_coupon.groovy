package com.jeancoder.market.entry.coupon


import java.util.regex.Matcher
import java.util.regex.Pattern

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.RemoteUtil


Result result= new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String b_idstr = request.getParameter("b_id");
	String give_coupon_mobiles = request.getParameter("give_coupon_mobiles");

	BigInteger pid = RemoteUtil.getProj().getId();
	BigInteger b_id = new BigInteger(b_idstr);

	if(b_id<=0){
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}

	List<String> mobiles = new ArrayList<String>();
	String [] attr_tmp = give_coupon_mobiles.split(",");
	
	Pattern p = java.util.regex.Pattern.compile(/^1[34578][0-9]{9}$/);
		
	for(String s:attr_tmp){
		Matcher matcher = p.matcher(s);
		if(!matcher.matches()){
			return result.setData(AvailabilityStatus.notAvailable("错误手机号码："+s));
		}
		mobiles.add(s);
	}
	CouponService.INSTANSE.give_out_coupons(b_id, mobiles, pid);
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	Logger.error("卡券发放失败", e);
	return result.setData(AvailabilityStatus.notAvailable("卡券发放失败"));
}