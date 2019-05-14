package com.jeancoder.market.entry.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String b_idstr = request.getParameter("b_id");
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String key = request.getParameter("key");
	
	if(StringUtil.isEmpty(key)){
		key="";
	}
	
	if(StringUtil.isEmpty(pn_s)){
		pn_s="1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="10";
	}
	BigInteger pn = new BigInteger(pn_s);
	if(pn<1){
		pn=1;
	}
	BigInteger ps = new BigInteger(ps_s);
	
	JcPage<CouponCode> page = new JcPage<CouponCode>();
	page.setPn(pn);
	page.setPs(ps);
	
	BigInteger b_id = new BigInteger(b_idstr);
	CouponBatch batch = CouponBatchService.INSTANSE.getItem(b_id);
	page = CouponService.INSTANSE.getList(key,b_id, page);
 
	JcPage<CouponCode> page1 = new JcPage<CouponCode>();
	page1.setPn(1);
	page1.setPs(10000);
		
	page1 = CouponService.INSTANSE.getList(key,b_id, page1);
	result.addObject("batch", batch);
	result.addObject("key", key);
	result.addObject("page", page);
	result.addObject("page1", page1);
	result.setView("coupon/details");
	return result;
}catch(Exception e){
	logger.error("查询卡券详情失败：", e);
	return result.setRedirectResource("index");
}