package com.jeancoder.market.entry.coupon.servpp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.dto.CouponDic
import com.jeancoder.market.ready.dto.SelectModel
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.ServicePack
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.ServicePackService
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	Long pid = RemoteUtil.getProj().getId();
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	
	//分页查询处理
	if(StringUtil.isEmpty(pn_s)){
		pn_s = "1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="10";
	}

	Integer pn = Integer.parseInt(pn_s);
	if(pn<1){
		pn=1;
	}

	Integer ps = Integer.parseInt(ps_s);

	JcPage<ServicePack> page = new JcPage<ServicePack>();
	page.setPn(pn);
	page.setPs(ps);

	//返回结果
	page = ServicePackService.INSTANSE.getList(pid, page);
	result.addObject("page", page);
	return 	result.setView("coupon/servpp/index");;
}catch(Exception e){
	Logger.error("查询失败", e);
	
	return 	result.setView("coupon/servpp/index").addObject("error_msg", "查询失败");
}
