package com.jeancoder.market.entry.coupon.destroy

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	def  pub_code = request.getParameter("pub_code");
	def phone_code = request.getParameter("phone_code");
	def pid = RemoteUtil.proj.id;
	pub_code = StringUtil.trim(pub_code);
	phone_code = StringUtil.trim(phone_code);
	if(StringUtil.isEmpty(pub_code) || StringUtil.isEmpty(phone_code)) {
		return AvailabilityStatus.notAvailable("参数不能为空");
	}
	List<CouponCode> account_coupons = CouponService.INSTANSE.get_account_coupon_nums(pid, phone_code);
	if(account_coupons==null||account_coupons.isEmpty()) {
		return AvailabilityStatus.notAvailable("券码和手机号码找不到对应的券信息");
	}
	Iterator<CouponCode> cc_its = account_coupons.iterator();
	Map<BigInteger, CouponBatch> map = new HashMap<BigInteger, CouponBatch>();
	while(cc_its.hasNext()) {
		CouponCode coupon_code = cc_its.next();
		if(!coupon_code.getCode().equals(pub_code)) {
			cc_its.remove();
			continue;
		}
		CouponBatch batch = map.get(coupon_code.batch_id)
		if (batch == null) {
			batch = CouponBatchService.INSTANSE.getById(coupon_code.batch_id);
			map.put(coupon_code.batch_id, batch);
		}
		coupon_code.batch = batch;
	}
	if(account_coupons==null||account_coupons.isEmpty()) {
		return AvailabilityStatus.notAvailable("券码和手机号码找不到对应的券信息");
	}
	return AvailabilityStatus.available(account_coupons);
}catch(Exception e){
	Logger.error("查询失败", e);
	return AvailabilityStatus.notAvailable("查询失败");
}
 