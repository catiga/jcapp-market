package com.jeancoder.market.entry.coupon.batch

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponRuleFather
import com.jeancoder.market.ready.entity.ServerCouponRule
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.MoneyUtil


Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger logger = LoggerSource.getLogger(this.getClass().getName());
try{
	String id_s = request.getParameter("id");
	String coupon_no = request.getParameter("coupon_no");
	String coupon_title = request.getParameter("coupon_title");
	String coupon_info = request.getParameter("coupon_info");
	String coupon_use_service = request.getParameter("coupon_use_service");//server
	String coupon_use_store = request.getParameter("coupon_use_store");//server
	String coupon_crapp = request.getParameter("coupon_crapp");
	String exchange_tpye = request.getParameter("exchange_tpye"); //coupon_type  兑换类型
	String rule_map = request.getParameter("rule_map");  //rule  兑换类型和值
	String quantitys_type = request.getParameter("quantitys_type");//数量策略 server
	String money_quantitys = request.getParameter("money_quantitys");
	String coupons_quantitys = request.getParameter("coupons_quantitys");
	String spru_time_type = request.getParameter("spru_time_type");//可用时间类型 server
	String spru_time_spec = request.getParameter("spru_time_spec");//server
	String coupon_validate_type = request.getParameter("coupon_validate_type");//设置失效时间
	String coupon_validate_period = request.getParameter("coupon_validate_period");
	String coupon_content = request.getParameter("coupon_content");
	String coupon_img = request.getParameter("coupon_img");//
	String p_lowest = request.getParameter("p_lowest");//最低价

	if("".equals(id_s)){
		return result.setData(AvailabilityStatus.notAvailable("卡券id为空"));
	}
	BigInteger id = new BigInteger(id_s);//batch主键
	// 检查不同类型兑换的参数
	String [] rule = rule_map.split("/");
	String rule_value;
	switch(exchange_tpye){
		case "1000":
			rule_value = MoneyUtil.get_cent_from_yuan(rule[0]);
			break;
		case "3000":
			if(rule.length.equals(2)){
				rule_value = rule[0]+"/"+MoneyUtil.get_cent_from_yuan(rule[1]);
			}else{
				rule_value = rule[0];
			}
			break;
		case "2000":
			if(rule.length.equals(3)){
				rule_value = rule[0]+"/"+rule[1]+"/"+MoneyUtil.get_cent_from_yuan(rule[2]);
			}else{
				rule_value = rule[0]+"/"+rule[1];
			}
			break;
	}

	spru_time_spec = DateUtil.compareW_to_R(spru_time_type,spru_time_spec);

	if ((coupon_crapp.equals("1000") || coupon_crapp.equals("2000"))) {
		return result.setData(AvailabilityStatus.notAvailable(JsConstants.input_param_error));
	}

	CouponBatch batch = new CouponBatch();
	ServerCouponRule scr = new ServerCouponRule();
	batch.id = id;
	batch.coupon_no = coupon_no;
	batch.title = coupon_title;
	batch.info = coupon_info;
	scr.batch_id = id;
	scr.server = coupon_use_service;
	scr.stores = coupon_use_store;
	batch.crapp = coupon_crapp;

	//兑换策略
	batch.coupon_type = exchange_tpye;

	CouponRuleFather crf = new CouponRuleFather();
	crf.rule_value = rule_value;
	scr.rule_value = rule_value;
	batch.couponRule = crf;

	//数量策略
	scr.quantitys_key = quantitys_type;
	if(""== money_quantitys&&""!=coupons_quantitys){
		scr.quantitys_value = coupons_quantitys;
	}
	if(""== coupons_quantitys&&""!=money_quantitys){
		scr.quantitys_value = MoneyUtil.get_cent_from_yuan(money_quantitys);
	}
	if(""!= money_quantitys && ""!= coupons_quantitys){
		scr.quantitys_value= MoneyUtil.get_cent_from_yuan(money_quantitys)+"/"+coupons_quantitys;
	}
	//有效时间段策略
	scr.time_type = spru_time_type;

	scr.time_value = spru_time_spec;

	//设置失效时间策略
	batch.validate_type = coupon_validate_type;
	batch.validate_period = coupon_validate_period;

	batch.content = coupon_content;
	batch.img = coupon_img;

	Integer upnums = CouponBatchService.INSTANSE.updateItem(batch, scr);
	if(upnums>0){
		return result.setData(AvailabilityStatus.available());
	}
	return result.setData(AvailabilityStatus.notAvailable("更新失败"));
}catch(Exception e){
	logger.error("编辑服务卡券失败");
	return result.setData(AvailabilityStatus.notAvailable("编辑服务卡券失败"));
}
