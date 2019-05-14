package com.jeancoder.market.entry.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.RemoteUtil


// 增加数量
Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String b_idStr =  request.getParameter("b_id");
	String incre_generate_numStr = request.getParameter("incre_generate_num");
	BigInteger pid = RemoteUtil.getProj().getId();

	Integer b_id = new Integer(b_idStr);
	Integer num = new Integer(incre_generate_numStr);
	if(b_id<=0||num<=0){
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}
	CouponBatch cb = CouponBatchService.INSTANSE.get_batch_by_id(b_id, pid);
	if("".equals(cb)){
		return AvailabilityStatus.notAvailable("查询不到对应卡券");
	}
	CouponService.INSTANSE.incre_coupon_batch(cb, num)
	return result.setData(AvailabilityStatus.available());
}catch(Exception e){
	logger.error("增量失败");
	return result.setData(AvailabilityStatus.notAvailable(JsConstants.unknown));
}