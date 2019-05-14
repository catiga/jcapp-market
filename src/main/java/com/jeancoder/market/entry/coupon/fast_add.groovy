package com.jeancoder.market.entry.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.dto.CouponDic
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger();
try{
	String cb_title = request.getParameter("cb_title");
	String cb_crapp = request.getParameter("cb_crapp");
	String cb_outer = request.getParameter("cb_outer");
	String cb_mcflk = request.getParameter("cb_mcflk");
	BigInteger pid = RemoteUtil.getProj().getId();
	//卡批次名字处理
	if (cb_title == null || cb_title.trim().equals("")) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	/*
	 * 卡应用类型   不是普通选座类，报参数输入错误
	 */
	if (!(cb_crapp.equals(CouponDic.COUPON_GENERAL.key())|| cb_crapp.equals(CouponDic.COUPON_TICKETS.key())||
	cb_crapp.equals(CouponDic.COUPON_APPOINT.key())||
	cb_crapp.equals(CouponDic.COUPON_ONLINE_SERVER.key()))) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	/*
	 * 卡券体系处理   不是内部卡券系统，报错
	 */
	if (cb_outer == null) {
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}

	if(!cb_outer.equals("0")){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	if(cb_mcflk==null){
		return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	}
	//保存卡批次
	CouponBatch cb = new CouponBatch();
	cb.title = cb_title
	cb.crapp = cb_crapp
	cb.is_outer = new Integer(cb_outer);
	cb.coupon_type = cb_mcflk;
	cb.coupon_no = generate_coupon_no(pid);
	Integer itmeId = CouponBatchService.INSTANSE.saveItem(pid,cb);
	if(itmeId>0){
		return result.setData(AvailabilityStatus.available(itmeId));
	}
	return result.setData(AvailabilityStatus.notAvailable(itmeId));
}catch(Exception e){
	logger.error("添加卡券批次失败");
	return result.setData(AvailabilityStatus.notAvailable("添加卡券批次失败"));
}


String generate_coupon_no(def pid) {
	String buffer = "sp" + pid.toString() + DateUtil.format_yyyyMMdd(new Date()) + this.nextInt(100, 999);
	buffer = buffer.replaceAll("\\-", "");
	return buffer;
}

int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
