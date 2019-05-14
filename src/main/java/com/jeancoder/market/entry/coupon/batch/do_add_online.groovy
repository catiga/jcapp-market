package com.jeancoder.market.entry.coupon.batch

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.AvailabilityStatus
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.StringUtil

JCRequest request =RequestSource.getRequest();
String id=request.getParameter("id")
String coupon_no=request.getParameter("coupon_no");
String coupon_title=request.getParameter("coupon_title");
String coupon_info=request.getParameter("coupon_info");
String coupon_img=request.getParameter("coupon_img");
String coupon_crapp=request.getParameter("coupon_crapp");
String coupon_content=request.getParameter("coupon_content");
String coupon_use_con_gs=request.getParameter("coupon_use_con_gs");
String coupon_use_val_gs=request.getParameter("coupon_use_val_gs");
String coupon_use_con_ss=request.getParameter("coupon_use_con_ss");
String coupon_use_val_ss=request.getParameter("coupon_use_val_ss");
String p_lowest=request.getParameter("p_lowest")
String quantitys_key=request.getParameter("quantitys_type")
String coupons_quantitys=request.getParameter("coupons_quantitys")
String movie_quantitys=request.getParameter("movie_quantitys")
String spru_time_type=request.getParameter("spru_time_type")
String spru_time_spec=request.getParameter("spru_time_spec")
String exchange_tpye=request.getParameter("exchange_tpye")
String rule_map=request.getParameter("rule_map")
String coupon_validate_type=request.getParameter("coupon_validate_type")
String coupon_validate_period=request.getParameter("coupon_validate_period")
List<Map<String,String>>  spru_time_list = null ;

// 检查不同类型兑换的参数
Map<String,String> rule_map_map = null ;
CouponBatch batch = new CouponBatch();
try{
	if (StringUtil.isEmpty(id) || StringUtil.isEmpty(coupon_no) || StringUtil.isEmpty(coupon_title)||StringUtil.isEmpty(coupon_info) ||
	StringUtil.isEmpty(coupon_crapp) || StringUtil.isEmpty(exchange_tpye)||StringUtil.isEmpty(rule_map)
	|| StringUtil.isEmpty(coupon_validate_type) || StringUtil.isEmpty(coupon_validate_period)) {
		return new Result().setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	spru_time_spec = DateUtil.compareW_to_R(spru_time_type,spru_time_spec);
	CouponBatch coupon=new CouponBatch();
	MovieCouponRule movie=new MovieCouponRule();
	coupon.id=new BigInteger(id.toString());
	movie.batch_id=new BigInteger(id.toString());
	coupon.coupon_no=coupon_no;
	coupon.title=coupon_title;
	coupon.info=coupon_info;
	coupon.crapp=coupon_crapp;
	coupon.img=coupon_img;
	coupon.content=coupon_content;
	movie.halls=coupon_use_val_gs;
	movie.cinema_type='';
	movie.movie_type=coupon_use_con_gs;
	movie.stores=coupon_use_con_ss;
	String film_no = "";
	if(coupon_use_val_ss){
		String [] movie_no = coupon_use_val_ss.split(",");
		for(int i=0;i<movie_no.length;i++){
			String [] s = movie_no[i].split("--");
			if(film_no){
				film_no +=",";
			}
			film_no += s[1];
		}
	}
	movie.movie=film_no;
	movie.lowest=new Integer(p_lowest.toString());
	movie.quantitys_key=quantitys_key;
	if (StringUtil.isEmpty(movie_quantitys)) {
		movie.quantitys_value=coupons_quantitys;
	}
	if (StringUtil.isEmpty(coupons_quantitys)) {
		movie.quantitys_value=MoneyUtil.get_cent_from_yuan(movie_quantitys)
	}
	if (!StringUtil.isEmpty(movie_quantitys)&&!StringUtil.isEmpty(coupons_quantitys)) {
		movie.quantitys_value=MoneyUtil.get_cent_from_yuan(movie_quantitys)+"/"+coupons_quantitys;
	}
	//	String map="";
	//	for(String key:rule_map_map.keySet())
	//		{
	//			map +=(rule_map_map.get(key)+"/");
	//		}
	//	String rule_value= map.substring(0,map.length()-1);
	String [] rule_maps=rule_map.split("/");
	if (exchange_tpye=="1000") {
		if (StringUtil.isEmpty(rule_map)) {
			return AvailabilityStatus.notAvailable("折扣不能为空")
		}
		movie.rule_value=MoneyUtil.get_cent_from_yuan(rule_map);
	}
	if (exchange_tpye=="3000") {
		if(rule_maps.length.equals(2)){
			movie.rule_value=rule_maps[0]+"/"+MoneyUtil.get_cent_from_yuan(rule_maps[1]);
		}else{
			movie.rule_value=rule_maps[0];
		}
	}
	if (exchange_tpye=="2000") {
		if(rule_maps.length.equals(3)){
			movie.rule_value=rule_maps[0]+"/"+rule_maps[1]+"/"+MoneyUtil.get_cent_from_yuan(rule_maps[2]);
		}else{
			movie.rule_value=rule_maps[0]+"/"+rule_maps[1];
		}
	}
	movie.time_type=spru_time_type;
	movie.time_value=spru_time_spec;
	coupon.coupon_type=exchange_tpye;
	//	movie.rule_value=rule_map;
	coupon.validate_type=coupon_validate_type
	coupon.validate_period=coupon_validate_period;
	String resultStr = CouponBatchService.INSTANSE.updateItem(coupon,movie);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
}catch(Exception e){
	e.printStackTrace();
	return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
}




