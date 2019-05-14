package com.jeancoder.market.entry.coupon.batch

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.OnlineServerCouponRule
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.MoneyUtil


/**
 * 更新一条商品
 */

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCResponse response = ResponseSource.getResponse();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String id = request.getParameter("id");
	String coupon_no = request.getParameter("coupon_no");
	String coupon_title = request.getParameter("coupon_title");
	String coupon_info = request.getParameter("coupon_info");
	String coupon_crapp = request.getParameter("coupon_crapp");
	String coupon_content = request.getParameter("coupon_content");
	String coupon_use_con_gs = request.getParameter("coupon_use_con_gs");
	String coupon_use_val_gs = request.getParameter("coupon_use_val_gs");
	String quantitys_type = request.getParameter("quantitys_type");
	String quantitys_value = request.getParameter("quantitys_value");
	String spru_time_type = request.getParameter("spru_time_type");
	String spru_time_spec = request.getParameter("spru_time_spec");
	String exchange_tpye = request.getParameter("exchange_tpye");
	String rule_map = request.getParameter("rule_map");
	String coupon_validate_type = request.getParameter("coupon_validate_type");
	String coupon_validate_period = request.getParameter("coupon_validate_period");
	if (StringUtil.isEmpty(id) || StringUtil.isEmpty(coupon_no) || StringUtil.isEmpty(coupon_title)||StringUtil.isEmpty(coupon_info) || StringUtil.isEmpty(coupon_crapp) ||StringUtil.isEmpty(quantitys_type)  || StringUtil.isEmpty(exchange_tpye)||StringUtil.isEmpty(rule_map) || StringUtil.isEmpty(coupon_validate_type) || StringUtil.isEmpty(coupon_validate_period)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	String[] rule_maps = rule_map.split("/");
	StringBuffer sf = new StringBuffer();
	switch(exchange_tpye) {
		case "1000":
			sf.append(MoneyUtil.get_cent_from_yuan(rule_map));
			break;
		case "3000":
			if(rule_maps.length.equals(2)){
				sf.append(rule_maps[0]+"/"+MoneyUtil.get_cent_from_yuan(rule_maps[1]));
			}else{
				sf.append(rule_maps[0]);
			}
			break;
		default:
			if(rule_maps.length.equals(3)){
				sf.append(rule_maps[0]+"/"+rule_maps[1]+"/"+MoneyUtil.get_cent_from_yuan(rule_maps[2]));
			}else{
				sf.append(rule_maps[0]+"/"+rule_maps[1]);
			}
			break;
	}
	rule_map = sf.toString();
	spru_time_spec = DateUtil.compareW_to_R(spru_time_type,spru_time_spec);
	//参数校验
	CouponBatch coupon=new CouponBatch();
	OnlineServerCouponRule gcr = new OnlineServerCouponRule();
	coupon.id=new BigInteger(id.toString());
	gcr.batch_id = new BigInteger(id.toString());
	coupon.coupon_no = coupon_no;
	coupon.title = coupon_title;
	coupon.info = coupon_info;
	coupon.crapp = coupon_crapp;
	coupon.content = coupon_content;
	gcr.goods = coupon_use_con_gs;
	gcr.stores = coupon_use_val_gs;
	coupon.coupon_type = exchange_tpye;
	gcr.rule_value = rule_map;
	gcr.quantitys_key = quantitys_type;
	gcr.quantitys_value = quantitys_value;
	gcr.time_type = spru_time_type;
	gcr.time_value = spru_time_spec;
	coupon.validate_type = coupon_validate_type;
	coupon.validate_period = coupon_validate_period;
	coupon.content = coupon_content;
	CouponBatchService.INSTANSE.updateItemgcr(coupon, gcr);
	return AvailabilityStatus.available();
} catch (Exception e) {
	Logger.error("保存商品卡券规则失败", e);
	return AvailabilityStatus.notAvailable("保存失败");
}