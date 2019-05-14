package com.jeancoder.market.entry.order

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.dto.CouponDic
import com.jeancoder.market.ready.dto.SelectModel
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.entity.OrderCoupon
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.OrderCouponServer
import com.jeancoder.market.ready.util.RemoteUtil
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try{
	Long pid = RemoteUtil.getProj().getId();
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String key = request.getParameter("key");
	if(StringUtil.isEmpty(key)){
		key = "";
	}
	//分页查询处理
	if(StringUtil.isEmpty(pn_s)){
		pn_s = "1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="20";
	}

	Integer pn = Integer.parseInt(pn_s);
	if(pn<1){
		pn=1;
	}

	Integer ps = Integer.parseInt(ps_s);

	JcPage<OrderCoupon> page = new JcPage<OrderCoupon>();
	page.setPn(pn);
	page.setPs(ps);
	//返回结果
	page = OrderCouponServer.INSTANSE.getPage(page,key,pid);

	String oders = '';
	for (def order: page.result) {
		oders += order.cbid+",";
	}
	
	if (oders != '') {
		oders = oders.substring(0,oders.length()-1);
	}
	if (oders != '') {
		String sql = "select * from CouponCode WHERE id in ("+oders+")";
		println sql;
		List<CouponCode> orderList = JcTemplate.INSTANCE.find(CouponCode,sql);
		for (def order: page.result) {
			 for (CouponCode code : orderList) {
				 if (order.cbid.toString().equals(code.id.toString())) {
					 order.code = code.code;
					 order.mobile = code.mobile;
				 }
			 }
		}
		
		
	}
	def ocp = [:]
	ocp['8000'] = '开卡订单'
	ocp['8001'] = '充值订单'
	ocp['1000'] = '商品订单'
	ocp['5000'] = '服务订单'
	ocp['2000'] = '影票订单'
	ocp['2010'] = '影票预约订单'
	 
	result.setView("order/use_order_list");
	result.addObject("key", key);
	result.addObject("oc", ocp);
	result.addObject("page", page);
	
	return result;
}catch(Exception e){
	Logger.error("查询不到卡批次");
	return result.addObject("error_msg", "查询卡批次失败");
}
