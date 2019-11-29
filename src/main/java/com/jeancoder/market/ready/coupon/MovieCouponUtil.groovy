package com.jeancoder.market.ready.coupon

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.dto.coupon.CouponRule
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.MovieCouponRuleServer
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.StringUtil

class MovieCouponUtil extends CouponUtilAbstract {
	//private static final JCLogger LOGGER = LoggerSource.getLogger(MovieCouponUtil.class.getName());
	private static final JCLogger LOGGER = JCLoggerFactory.getLogger('MOVIECOUPONUTIL');
	@Override
	public def getCouponDto(def batch, def couponCode, Object rule) {
		def dto = super.getCouponDto(batch, couponCode, rule);
		def rules  = []
		if (CouponConstants._coupon_type_voucher_.equals(batch.coupon_type)) {
			//代金劵
			String amount = MoneyUtil.get_yuan_from_cent(dto.rule_value);
			amount = MoneyUtil.outEndZero(amount);
			rules.add(amount + "元代金");
		}
		if (CouponConstants._coupon_type_exchange_.equals(batch.coupon_type)) {
			// 兑换劵
			String[] values = dto.rule_value.split("/");
			if (values.length == 3) {
				rules.add(values[0] + "张卡劵兑换" + values[1] + "张电影票补"+ MoneyUtil.get_yuan_from_cent(values[2]) + "元")
			}
		}
		if (CouponConstants._coupon_type_discount_.equals(batch.coupon_type)) {
			// 折扣劵
			String[] values = dto.rule_value.split("/");
			String discount = MoneyUtil.divide(values[0], "10");
			rules.add(MoneyUtil.outEndZero(discount) + "折折扣");
		}
		dto.rule = rules;
		return dto;
	}

	@Override
	public def isOrderAvailable(def batch, def couponCode, Object rule, Object order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public def consumeCouponPrice(List<CouponCode> couponCodes, BigInteger sid, def order, def totalAmount, def param) {
		def hall_id = param['hall_id']
		def film_no = param['film_no'];
		CouponRule couponPrice = new CouponRule();
		couponPrice.success = false;
		String offerAmount = "0";
		try {
			//可用卡券数量
			def ticket_sum = 0;
			CouponBatch batch = CouponBatchService.INSTANSE.getById(couponCodes.get(0).batch_id);
			if (batch == null) {
				couponPrice.msg = "未找到卡劵批次";
				return couponPrice;
			}
			MovieCouponRule goodsCouponRule = MovieCouponRuleServer.INSTANSE.getRuleByBathId(couponCodes.get(0).batch_id);
			if (goodsCouponRule == null) {
				couponPrice.msg = "未找到卡劵规则";
				return couponPrice;
			}
			//判断是否有可用的门店、影厅
			// 判断影城是否符合
			LOGGER.info('goodsCouponRule.stores===' + goodsCouponRule.stores + ', and s_id====' + sid);
			if(!StringUtil.isEmpty(goodsCouponRule.stores)){
				boolean flag = true;
				String[] store_ids = goodsCouponRule.stores.split(",");
				for (def s_id : store_ids) {
					if (s_id.equals(sid.toString())) {
						flag = false;
						break;
					}
				}
				if(flag) {
					couponPrice.msg = "该卡券不适用该影城"
					return couponPrice;
				}
			}
			 
			// 判断影厅是否符合
			if(!StringUtil.isEmpty(goodsCouponRule.halls)){
				boolean flag = true;
				String[] hall_ids = goodsCouponRule.halls.split(",");
				for (def h_id : hall_ids) {
					def hall = h_id.split("-")
					if (hall[0].equals(sid.toString()) && hall[1].equals(hall_id.toString())) {
						flag = false;
						break;
					}
				}
				if(flag) {
					couponPrice.msg = "该卡券不适用该影厅"
					return couponPrice;
				}
			}
 
			//影片判断
			if (!StringUtil.isEmpty(goodsCouponRule.movie)) {
				boolean flag = true;
				String [] movie = goodsCouponRule.movie.split(",");
				for (int i=0;i<movie.length;i++) {
					LOGGER.info(movie[i] + "_______"+film_no)
					if(movie[i].equals(film_no.toString())){
						flag = false;
						break;
					}
				}
				if(flag){
					couponPrice.msg = "该卡券不适用该影片"
					return couponPrice;
				}
			}
			
			//判断状态能不能用和有没有失效
			for (ccd in couponCodes) {
				//if(!ccd.status.equals(CouponConstants._coupon_code_status_touse_)){
				if(!ccd.status.equals(CouponConstants._coupon_code_status_touse_) && !ccd.status.equals(CouponConstants._coupon_code_status_available_)){
					couponPrice.msg = "卡券状态不可用";
					return couponPrice;
				}
				Long validate_end = null;
				if (CouponConstants._coupon_validate_type_forever_.equals(ccd.validate_type)) {
					// 永久有效
					continue;
				} else if (CouponConstants._coupon_validate_type_unidied_.equals(ccd.validate_type)) {
					// 固定日期有效
					Date validateDate =  DateUtil.getDate(ccd.validate_period, "yyyy-MM-dd HH:mm:ss");
					if (validateDate != null) {
						validate_end = validateDate.getTime();
					}
				} else if (CouponConstants._coupon_validate_type_days_.equals(ccd.validate_type)) {
					// 按领用天数失效
					Long getTime = ccd.get_time.getTime();
					validate_end = getTime + Integer.parseInt(ccd.validate_period)*24*60*60*1000;
				}
				if (validate_end == null) {
					couponPrice.msg = "卡劵配置错误"
					return couponPrice;
				}
				if (validate_end < new Date().getTime()) {
					couponPrice.msg = "卡劵过期";
					return couponPrice;
				}
			}

			//判断是否满足时间策略按周分时设置

			if (goodsCouponRule.time_type.equals(CouponConstants._TIME_WEEK_SHARING_)) {
				String[] spru_time_list = goodsCouponRule.time_value.split("/");
				boolean is_week = false;
				for(String time :spru_time_list){
					String[] times = time.split(";");
					String week_days = times[0];
					String start = times[1];
					String end = times[2];
					// 只要符合一个时间限制就能访问
					if(DateUtil.period_online_week(week_days, start, end, new Date())){
						is_week = true;
						break;
					}
				}
				if(!is_week){
					couponPrice.msg = "该卡劵不支持当前使用";
					return couponPrice;
				}
			} else if (goodsCouponRule.time_type.equals(CouponConstants._TIME_DAY_SHARING_)) {
				String[] spru_time_list = goodsCouponRule.time_value.split("/");
				boolean is_week = false;
				for(String time :spru_time_list){
					String[] times = time.split(";");
					if(times.length != 4){
						couponPrice.msg = "该卡劵配置错误";
						return couponPrice;
					}
					String s_date = times[0];
					String e_date = times[1];
					String start = times[2];
					String end = times[3];
					// 只要符合一个时间限制就能访问
					if(DateUtil.period_online_date(s_date, e_date, start, end, new Date())){
						is_week = true;
						break;
					}
				}
				if(!is_week){
					couponPrice.msg = "该卡劵不支持当前使用";
					return couponPrice;
				}
			}

			//			// 数量策略判断
			String org_sale_fee = totalAmount;  // 总金额
			String offset_fee = "0";   // 优惠价格
			// 计算总价
			// 数量策略
			def goodList = getByPriceDes(order);
			int moveSum = goodList.size();// 商品总数量
			int sum =  0; // 可使用卡劵数量 初始值
			String quantitys_type =  goodsCouponRule.quantitys_key
			String[] quantitys_values = null;
			if (!StringUtil.isEmpty(goodsCouponRule.quantitys_value)) {
				quantitys_values = goodsCouponRule.quantitys_value.split("/")
			}
			// = goodsCouponRule.quantitys_value.split("/");
			if(CouponConstants._QUANTITYS_TYPE_FULL_.equals(quantitys_type)){ // 满多少分可用
				if(new BigDecimal(MoneyUtil.add(org_sale_fee, quantitys_values[0])) >= 0){
					sum = Integer.parseInt(quantitys_values[1]);
				}
			}else if(CouponConstants._QUANTITYS_TYPE_EVERY_FULL_.equals(quantitys_type)){ // 每满多少分可以用
				sum = Integer.parseInt(MoneyUtil.roundingDown(MoneyUtil.divide(totalAmount,quantitys_values[1])));
			}else if(CouponConstants._QUANTITYS_TYPE_EVERY_PEN_.equals(quantitys_type)){ // 每笔交易最多用多少张
				sum = Integer.parseInt(quantitys_values[0]);
			}else if(CouponConstants._QUANTITYS_TYPE_EVERYS_ONE_.equals(quantitys_type)){ // 每个商品可以用多少张卡劵
				sum = moveSum * Integer.parseInt(quantitys_values[0]);
			}else if(CouponConstants._QUANTITYS_TYPE_NO_.equals(quantitys_type)){ // 无设置
				sum = -100;
			}else{
				couponPrice.msg = "卡劵配置错误";
				return couponPrice;
			}

			if(sum == 0 ){
				couponPrice.msg = "订单不满足使用卡劵";
				return couponPrice;
			}

			def couponSun = couponCodes.size();
			String rule_type = batch.coupon_type;
			String value = goodsCouponRule.rule_value;
			if(CouponConstants._coupon_type_voucher_.equals(rule_type)){   //代金
				String kims_money  = value;
				if( sum != -100  &&  couponSun > sum ){
					couponPrice.msg = "卡劵使用数量错误";
					return couponPrice;
				}
				offerAmount = MoneyUtil.multiple(kims_money, couponSun+"");
			}else if(CouponConstants._coupon_type_exchange_.equals(rule_type)){   //兑换 劵不受数量限制
				String payAmount = "0";
				String[] values = value.split("/");
				// 输入卡劵的数量必须是 兑换条件中卡劵数量的整数倍
				int coupons  = Integer.parseInt(values[0]); // 卡劵数量
				int goodss  = Integer.parseInt(values[1]); // 商品数量
				String exchange_money =  "0"   //  补差价
				if (couponSun % coupons != 0) {
					couponPrice.msg = "卡劵使用数量错误";
					return couponPrice;
				}
				if ( values.length ==3 && !StringUtil.isEmpty(exchange_money)) {
					exchange_money = values[2];
				}
				Integer  exchangeSum = couponSun / coupons;// 可以兑换几组商品
				Integer exchange_goods = exchangeSum * goodss;// 当前卡劵可以兑换的商品数量
				if (exchange_goods >= moveSum) {
					payAmount = MoneyUtil.multiple(exchangeSum.toString(), exchange_money);
					offerAmount =  MoneyUtil.add(totalAmount, "-"+payAmount);
				} else {
					offerAmount = "0";
					String exchangeAmount = MoneyUtil.multiple(exchangeSum.toString(), exchange_money);// 兑换劵 需要补的总差价
					String exchangeOriginalAmount = "0";//兑换劵兑换商品的原总价
					for (int i = 0; i < exchange_goods; i++) {
						exchangeOriginalAmount = MoneyUtil.add(exchangeOriginalAmount, goodList[i][1].toString());
					}
					offerAmount = MoneyUtil.add(exchangeOriginalAmount, "-"+exchangeAmount);
				}
			} else if(CouponConstants._coupon_type_discount_.equals(rule_type)){   //折扣
				String[] values = value.split("/");
				String discount = values[0]
				String discount_money = "";
				if  (values.length > 1) {
					discount_money = values[1];
				}
				if( sum != -100  &&  couponSun > sum ){
					couponPrice.msg = "卡劵使用数量错误";
					return couponPrice;
				}
				String money = totalAmount;
				for(int ii = 0; ii < couponSun; ii++){
					money = MoneyUtil.multiple(money, discount);
					money = MoneyUtil.divide(money, "100");
					if(!StringUtil.isEmpty(discount_money)){ // 最高折口金额为空时  则没有限制
						String newOfferAmount = MoneyUtil.add(totalAmount, "-"+money);
						BigDecimal bd = new BigDecimal(MoneyUtil.add(newOfferAmount, "-"+discount_money));
						if (bd >= 0) { //
							// 如果当前折扣大于最高折扣金额，则取最大折扣金额为优惠金额
							money = MoneyUtil.add(totalAmount, "-"+discount_money);
							break;
						}
					}
				}
				println "money" + money;
				offerAmount = MoneyUtil.add(totalAmount, "-"+money);
			}else{    //无
				couponPrice.msg = "卡劵规则错误";
				return couponPrice;
			}

			offerAmount = MoneyUtil.rounding(offerAmount);

			if (new  BigDecimal(MoneyUtil.add(totalAmount,"-"+offerAmount)) < 0) {
				offerAmount = totalAmount;
			}
			couponPrice.success = true;
			couponPrice.offerAmount = offerAmount;
			couponPrice.totalAmount = totalAmount;
			return couponPrice;
		} catch (Exception e) {
			LOGGER.error("卡劵计算失败",e);
			couponPrice.msg = "计算失败"
			return couponPrice;
		}
	}

	/**
	 * [[goods_id,tycode,cat_ids,price,num],[goods_id,tycode,cat_ids,price,num]]
	 * @param g
	 * @return
	 */
	private static def getByPriceDes(def g) {
		def list = [];
		// 按单价降序排序
		for (def dto : g) {
			boolean flag = false;
			for(int i = 0; i < list.size(); i++){
				def item = list.get(i);
				if(new BigDecimal(MoneyUtil.add(dto[1].toString(),"-" +item[1].toString())) >= 0){
					list.add(i,dto);
					flag = true;
					break;
				}
			}
			if(!flag){
				list.add(dto);
			}
		}
		return list;
	}
}
