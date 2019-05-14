package com.jeancoder.market.ready.util

class CouponUtil {
	
//	public static CouponRule is_coupon_valid(String coupon_type,
//		String coupon_use_con_money, Integer coupon_use_val_money,
//		String coupon_use_con_gs, String coupon_use_val_gs,
//		String coupon_use_con_ss, String coupon_use_val_ss,
//		Integer coupon_val,Integer ticsum,Integer film_type_key,String film_type_val,
//		String poucut) {
//	CouponRule cr = new CouponRule();
//	
//	if(coupon_use_con_money.equals(CouponConstants._coupon_use_con_money_unlimit_)) {
//		coupon_use_val_money = 0;
//	} else {
//		if(coupon_use_val_money<=0) {
//			throw new UnvalidValueException();
//		}
//	}
//	if(coupon_use_con_gs.equals(CouponConstants._coupon_use_con_gs_unlimit_)) {
//		coupon_use_val_gs = "0";
//	} else if(coupon_use_con_gs.equals(CouponConstants._coupon_use_con_gs_aim_goods_)||
//			coupon_use_con_gs.equals(CouponConstants._coupon_use_con_gs_aim_goods_cats_)) {
//		if(coupon_use_val_gs==null||coupon_use_val_gs.trim().equals("")) {
//			throw new UnvalidValueException();
//		}
//	}
//	if(coupon_use_con_ss.equals(CouponConstants._coupon_use_con_ss_unlimit_)) {
//		//不限制门店
//		coupon_use_val_ss = "0";
//	} else {
//		if(coupon_use_val_ss==null||coupon_use_val_ss.trim().equals("")) {
//			throw new UnvalidValueException();
//		}
//	}
//	
//	if(coupon_type.equals(CouponConstants._coupon_type_voucher_) || coupon_type.equals(CouponConstants._coupon_type_exchange_)) {
//		//校验代金券
//		coupon_val = coupon_val * 100;
//	}
//	
//	CouponMoneyRule cmr = new CouponMoneyRule();
//	cmr.setCucm(coupon_use_con_money);
//	cmr.setCuvm(MoneyUtil.get_cent_from_yuan(coupon_use_val_money.toString()));
//	
//	CouponGsRule cgr = new CouponGsRule();
//	cgr.setCucg(coupon_use_con_gs);
//	cgr.setCuvg(coupon_use_val_gs);
//	
//	CouponSsRule csr = new CouponSsRule();
//	csr.setCucs(coupon_use_con_ss);
//	csr.setCuvs(coupon_use_val_ss);
//	
//	cr.setCgr(cgr);
//	cr.setCmr(cmr);
//	cr.setCsr(csr);
//	cr.setCrval(coupon_val.toString());
//	
//	cr.setTicsum(""+ticsum);
//	CouponFtRule ftr = new CouponFtRule();
//	ftr.setCucs(""+film_type_key);
//	ftr.setCuvs(film_type_val);
//	cr.setFtr(ftr);
//	
//	cr.setPoucut(poucut);
//	
//	return cr;
//}
//
//
//public static CouponRule is_coupon_valid_online(
//		String coupon_use_con_gs, String coupon_use_val_gs,
//		String coupon_use_con_ss, String coupon_use_val_ss,
//		String quantitys_type,String movie_quantitys,String coupons_quantitys,  // 数量策略
//		String spru_time_type, List<Map<String,String>> spru_time_list, // 时间策略
//		String exchange_tpye, Map<String,String> rule_map,  // 兑换规则
//		String p_lowest) {    // 是否保护最低价
//	
//	CouponRule cr = new CouponRule();
//	//兼容之前的版本
//	CouponGsRule cgr = new CouponGsRule();
//	cgr.setCucg("-1000");
//	cgr.setCuvg("3");
//	CouponSsRule csr = new CouponSsRule();
//	csr.setCucs("3000");
//	csr.setCuvs("0");
//	cr.setCgr(cgr);
//	CouponMoneyRule cmr = new CouponMoneyRule();
//	cmr.setCucm("0");
//	cmr.setCuvm(MoneyUtil.get_cent_from_yuan("0".toString()));
//	
//	
//	cr.setVersion("1000"); //  新版本的卡劵
//	
//	// 在线选票新版本
//	cr.setXyc(coupon_use_con_ss); // 限制影城
//	cr.setXyp(coupon_use_val_ss); // 限制影片
//	cr.setYpt(coupon_use_con_gs); // 影片类型
//	cr.setYtt(coupon_use_val_gs);// 影厅类型
//	cr.setCsr(csr);
//	Map<String,Object> rule = new HashMap<String,Object>();
//	
//	
//	//添加时间策略
//	Map<String,Object>	time_map = new HashMap<String,Object>();
//	if(CouponConstants._TIME_NO_SHARING_.equals(spru_time_type)){ //  全天有效
//		time_map.put(spru_time_type,"null");
//	}else if(CouponConstants._TIME_WEEK_SHARING_.equals(spru_time_type) || CouponConstants._TIME_DAY_SHARING_.equals(spru_time_type)){ // w 按周分时   d按天分时
//		if(spru_time_list == null || spru_time_list.size() == 0){
//			throw new UnvalidValueException();
//		}
//		time_map.put(spru_time_type,spru_time_list);
//	}else{
//		throw new UnvalidValueException();
//	}
//	time_map.put("type", spru_time_type);
//	rule.put("time_map", time_map);
//   
//	
//	// 添加数量策略
//	Map<String,Object>	quantitys_map = new HashMap<String,Object>();
//	if(CouponConstants._QUANTITYS_TYPE_NO_.equals(quantitys_type)){ //  无
//		quantitys_map.put(quantitys_type,"null");
//	//102  101
//	}else if(CouponConstants._QUANTITYS_TYPE_EVERY_FULL_.equals(quantitys_type) || CouponConstants._QUANTITYS_TYPE_FULL_.equals(quantitys_type)){
//		if(movie_quantitys == null || movie_quantitys.length() == 0 ||  coupons_quantitys == null   ||  coupons_quantitys.length() == 0){
//			throw new UnvalidValueException();
//		}
//		quantitys_map.put(JsConstants.MOVIE_QUANTITYS,movie_quantitys);
//		quantitys_map.put(JsConstants.COUPONS_QUANTITYS,coupons_quantitys);
//	//104   103
//	}else if(CouponConstants._QUANTITYS_TYPE_EVERYS_ONE_.equals(quantitys_type) || CouponConstants._QUANTITYS_TYPE_EVERY_PEN_.equals(quantitys_type)){
//		quantitys_map.put(JsConstants.COUPONS_QUANTITYS,coupons_quantitys);
//	}else{
//		throw new UnvalidValueException();
//	}
//	
//	quantitys_map.put("type", quantitys_type);
//	rule.put(JsConstants.QUANTITYS_MAP, quantitys_map);
//	
//	// 添加兑换策略
//	Map<String,Object>	rule_map_map = new HashMap<String,Object>();
//	if(rule_map == null || rule_map.size() == 0){
//		throw new UnvalidValueException();
//	}
//	
//	if(CouponConstants._coupon_type_voucher_.equals(exchange_tpye) &&  rule_map.size() == 1){  // 代金
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else if(CouponConstants._coupon_type_exchange_.equals(exchange_tpye) && rule_map.size() == 3){  // 兑换
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else if(CouponConstants._coupon_type_discount_.equals(exchange_tpye) && rule_map.size() == 2){ // 折扣
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else{
//		throw new UnvalidValueException();
//	}
//	rule_map_map.put("type", exchange_tpye);
//	rule.put("rule_map_map", rule_map_map);
//	
//	// 判断是否需要保护最低价
//	if( !CouponConstants._P_LOWEST_YES.equals(p_lowest) &&  !CouponConstants._P_LOWEST_NOT.equals(p_lowest)){
//		throw new UnvalidValueException();
//	}
//	rule.put("p_lowest", p_lowest);
//	cr.setRuleMap(rule);
//	return cr;
//}
//
//
//public static CouponServletRule is_coupon_valid_service(
//		String coupon_use_con_ss, String coupon_use_val_ss,
//		String quantitys_type,String movie_quantitys,String coupons_quantitys,  // 数量策略
//		String exchange_tpye, Map<String,String> rule_map ) {    // 兑换规则
//	
//	CouponServletRule cr = new CouponServletRule();
//	cr.setVersion("1000"); //  新版本的卡劵
//	// 在线选票新版本
//	cr.setXyc(coupon_use_con_ss); // 限制影城
//	cr.setXyp(coupon_use_val_ss); // 限制影片
//	Map<String,Object> rule = new HashMap<String,Object>();
//	
//	// 添加数量策略
//	Map<String,Object>	quantitys_map = new HashMap<String,Object>();
//	if(CouponConstants._QUANTITYS_TYPE_NO_.equals(quantitys_type)){ //  无
//		quantitys_map.put(quantitys_type,"null");
//	//102  101
//	}else if(CouponConstants._QUANTITYS_TYPE_EVERY_FULL_.equals(quantitys_type) || CouponConstants._QUANTITYS_TYPE_FULL_.equals(quantitys_type)){
//		if(movie_quantitys == null || movie_quantitys.length() == 0 ||  coupons_quantitys == null   ||  coupons_quantitys.length() == 0){
//			throw new UnvalidValueException();
//		}
//		quantitys_map.put(JsConstants.MOVIE_QUANTITYS,movie_quantitys);
//		quantitys_map.put(JsConstants.COUPONS_QUANTITYS,coupons_quantitys);
//	//104   103
//	}else if(CouponConstants._QUANTITYS_TYPE_EVERYS_ONE_.equals(quantitys_type) || CouponConstants._QUANTITYS_TYPE_EVERY_PEN_.equals(quantitys_type)){
//		quantitys_map.put(JsConstants.COUPONS_QUANTITYS,coupons_quantitys);
//	}else{
//		throw new UnvalidValueException();
//	}
//	
//	quantitys_map.put("type", quantitys_type);
//	rule.put(JsConstants.QUANTITYS_MAP, quantitys_map);
//	
//	// 添加兑换策略
//	Map<String,Object>	rule_map_map = new HashMap<String,Object>();
//	if(rule_map == null || rule_map.size() == 0){
//		throw new UnvalidValueException();
//	}
//	
//	if(CouponConstants._coupon_type_voucher_.equals(exchange_tpye) &&  rule_map.size() == 1){  // 代金
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else if(CouponConstants._coupon_type_exchange_.equals(exchange_tpye) && rule_map.size() == 3){  // 兑换
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else if(CouponConstants._coupon_type_discount_.equals(exchange_tpye) && rule_map.size() == 2){ // 折扣
//		rule_map_map.put(exchange_tpye, rule_map);
//	}else{
//		throw new UnvalidValueException();
//	}
//	rule_map_map.put("type", exchange_tpye);
//	rule.put("rule_map_map", rule_map_map);
//	
//	cr.setRuleMap(rule);
//	return cr;
//}
//public static String re_compute_order_pay_amount(OrderInfo order, CouponCode coupon_code) {
//	boolean could_use = true;
//	if(!could_use) {
//		throw new UnsupportException();
//	}
//	CouponBatch coupon = coupon_code.getBatch();
//	//开始校验订单和coupon条件
//	String coupon_type = coupon.getCouponType();
//	String coupon_validate_type = coupon.getValidateType();
//	String coupon_validate_period = coupon.getValidatePeriod();
//	
//	CouponRule coupon_rule = coupon.getCr();
//	
//	/*************************************** 首先校验日期 ***************************************/
//	if(coupon_validate_type.equals(CouponConstants._coupon_validate_type_unidied_)) {
//		//统一时间失效
//		Date end_time = DateUtil.parse_yyyyMMddHHmmss(coupon_validate_period);
//		Date now_time = new Date();
//		if(now_time.after(end_time)) {
//			throw new ExpiredException();
//		}
//	} else if(coupon_validate_type.equals(CouponConstants._coupon_validate_type_days_)) {
//		//按零用后的天数
//		Date get_time = coupon_code.getGetTime();
//		Date now_time = new Date();
//		long diff = now_time.getTime() - get_time.getTime();
//		try {
//			long aim_days_time = Long.valueOf(coupon_validate_period)*24*60*60*1000;
//			if(diff>=aim_days_time) {
//				throw new ExpiredException();
//			}
//		} catch(Exception e) {
//			throw new ExpiredException();
//		}
//	}
//	/*************************************** 校验日期通过 ***************************************/
//	
//	/*************************************** 开始校验金额条件 ***************************************/
//	BigDecimal order_total_amount = new BigDecimal(order.getTotalAmount());
//	CouponMoneyRule coupon_money_rule = coupon_rule.getCmr();
//	if(coupon_money_rule.getCucm().equals(CouponConstants._coupon_use_con_money_full_to_off_)) {
//		//限制订单金额满减
//		BigDecimal bd = new BigDecimal(coupon_money_rule.getCuvm());
//		
//		if(order_total_amount.subtract(bd).intValue() < 0) {
//			throw new UnsupportException();
//		}
//	}
//	/*************************************** 校验金额条件通过 ***************************************/
//	
//	/*************************************** 开始校验商品条件 ***************************************/
//	/*************************************** 校验商品条件通过 ***************************************/
//	
//	/*************************************** 开始计算减掉的金额 ***************************************/
//	BigDecimal bd_val = new BigDecimal(coupon_rule.getCrval());
//	String need_pay_amount = "0";
//	if(coupon_type.equals(CouponConstants._coupon_type_voucher_)) {
//		//代金券
//		need_pay_amount = order_total_amount.subtract(bd_val).intValue() + "";
//	} else if(coupon_type.equals(CouponConstants._coupon_type_discount_)) {
//		//折扣券
//		BigDecimal bd_100 = new BigDecimal(100);
//		BigDecimal bd_diff = bd_100.subtract(bd_val);
//		if(bd_diff.intValue()<=0) {
//			throw new UnsupportException();
//		}
//		need_pay_amount = order_total_amount.multiply(bd_diff).divide(bd_100).intValue() + "";
//	} else if(coupon_type.equals(CouponConstants._coupon_type_exchange_)) {
//		//兑换券
//		//直接就是兑换商品
//		need_pay_amount = "0";
//	}
//	return need_pay_amount;
//}
}
