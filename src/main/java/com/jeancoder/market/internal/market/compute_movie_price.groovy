package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.coupon.CouponFactoryUtil
import com.jeancoder.market.ready.dto.coupon.CouponRule
import com.jeancoder.market.ready.dto.market.GoodsDto
import com.jeancoder.market.ready.dto.market.MCCompute
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketMobileBuy
import com.jeancoder.market.ready.entity.MarketMobileLimit
import com.jeancoder.market.ready.entity.MarketRuleTcss
import com.jeancoder.market.ready.service.CouponBatchService
import com.jeancoder.market.ready.service.CouponService
import com.jeancoder.market.ready.util.DirectComputePrice
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.MoneyUtil
import com.jeancoder.market.ready.util.StringUtil
import com.jeancoder.market.ready.util.TotalDateUtil

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName()+".compute_movie_price");
def t_num =   new Date().getTime();
t_num = t_num.toString() + new Random().nextInt(1000).toString();
MCCompute mcc = new MCCompute();

def p = JC.internal.param("p");
def sid = JC.internal.param("sid");
def pid = JC.internal.param("pid");
def on = JC.internal.param("on");
def oc = JC.internal.param("oc");
String op = JC.internal.param("op");

def mobile = JC.internal.param('mobile');
def ap_id = JC.internal.param('ap_id');

Logger.info('compute_movie_price:{t_num=' + t_num + ',p='+p + ',sid=' + sid + ',pid=' + pid + ',op=' + op + ', mobile=' + mobile + ', ap_id=' + ap_id);

try {
	
	if (StringUtil.isEmpty(p)|| pid == null) {
		mcc.code = JsConstants.input_param_null;
		mcc.msg = "参数不能为空";
		return mcc;
	}
	if (!StringUtil.isEmpty(op) && !"use".equals(op)) {
		mcc.code = "1000";
		mcc.msg = "操作类型错误";
		return mcc;
	}
	Logger.info('ppppppp=====' + p);
	// 转换参数
	def deto  = JackSonBeanMapper.jsonToMap(p);
	pid = new BigInteger(pid.toString());
	if (sid != null) {
		sid = new BigInteger(sid.toString());
	}
	// 参数
	String market_id = deto.market_id;
	if (StringUtil.isEmpty(market_id)) {
		mcc.code = "1000";
		mcc.msg = JsConstants.input_param_null;
		return mcc;
	}
	//查找活动
	MarketInfo market = JcTemplate.INSTANCE().get(MarketInfo, 'select * from MarketInfo where flag!=? and id=?', -1, market_id);
	if(market==null) {
		mcc.code = '1000';
		mcc.msg = '活动已经下架';
		return mcc;
	}
	if(market.mstatus!=MarketConstants._market_status_ing_) {
		mcc.code = '1000';
		mcc.msg = '活动已经暂停';
		return mcc;
	}
	//查找活动规则，直接写死，先查找选座活动
	def sql = 'select * from MarketRuleTcss where flag!=? and market_id=?';
	def params = []; params.add(-1); params.add(market.id);
	if(sid) {
		sql += ' and ( store_id=? or store_id is null )';
		params.add(sid);
	}
	sql += ' order by c_time desc';
	List<MarketRuleTcss> rules = JcTemplate.INSTANCE().find(MarketRuleTcss, sql, params.toArray());
	if(!rules || rules.empty) {
		mcc.code = '1000';
		mcc.msg = '活动规则错误';
		return mcc;
	}
	//开始查找匹配的规则
	if(market.limit_user!=0) {
		//说明限制了用户
		MarketMobileLimit limits = JcTemplate.INSTANCE().get(MarketMobileLimit, 'select * from MarketMobileLimit where flag!=? and market_id=? and mobile=?', -1, market.id, mobile);
		if(limits==null) {
			//说明用户不在名单里面，不允许使用
			mcc.code = '1000';
			mcc.msg = '手机号不在优惠活动范围内';
			return mcc;
		}
	}
	//找到目标唯一的规则
	MarketRuleTcss single_rule = rules.get(0);
	/**
	 * 20/2d:15;20/3d:25
	 * 00/99
	 */
	def price_policy = single_rule.price_policy;
	
	/**
	 * 2,m,
	 * 8,-1,	//活动哪总数量
	 * 3,w,3,5	//按照
	 */
	def num_policy = single_rule.number_policy;	
	def arr_num_policy = num_policy.split(',');
	def unit_total_num = Integer.valueOf(arr_num_policy[0]);
	def unit_total_unit = arr_num_policy[1];
	
	//需要查找该手机号目前已经购票的数量
	sql = 'select * from MarketMobileBuy where flag!=? and market_id=? and mobile=?';
	def compute_params = []; compute_params.add(-1); compute_params.add(market.id); compute_params.add(mobile);
	if(unit_total_unit=='-1') {
		//要看活动内总数量
		
	} else if(unit_total_unit=='w') {
		//要看每周总数量
		String week_1 = TotalDateUtil.get_now_time_week_one() + ' 00:00:00';
		String week_7 = TotalDateUtil.get_now_time_week_seven() + ' 23:59:59';
		sql += ' and a_time>=? and a_time<=?';
		compute_params.add(week_1);
		compute_params.add(week_7);
	} else if(unit_total_unit=='m') {
		//要看每月总数量
		String mon_1 = TotalDateUtil.get_now_time_month_one() + ' 00:00:00';
		String mon_7 = TotalDateUtil.get_now_time_month_last() + ' 23:59:59';
		sql += ' and a_time>=? and a_time<=?';
		compute_params.add(mon_1);
		compute_params.add(mon_7);
	} else if(unit_total_unit=='d') {
		//要看每天总数量
		String day_fir = TotalDateUtil.get_now_time() + ' 00:00:00';
		String day_las = TotalDateUtil.get_now_time() + ' 23:59:59';
		sql += ' and a_time>=? and a_time<=?';
		compute_params.add(day_fir);
		compute_params.add(day_las);
	}
	List<MarketMobileBuy> mobile_buy_list = JcTemplate.INSTANCE().find(MarketMobileBuy, sql, compute_params.toArray());
	if(mobile_buy_list!=null && mobile_buy_list.size()>=unit_total_num) {
		mcc.code = '1000';
		mcc.msg = '已经参加过该活动';
		return mcc;
	}
	//开始返回，并计算价格
	String film_no = deto.film_no;
	String film_dimen = deto.film_dimen;
	
	String totalAmount = "0";
	List<GoodsDto> goods_List = new ArrayList<GoodsDto>();
	
	// deto.g     [x.seat_no,x.sale_fee, x.pub_fee, order.hall_id]
	for(dto in deto.g) {
		def goods_id = dto[0];
		def price = dto[1];
		
		//构建单座位票价对象
		GoodsDto g1 = new GoodsDto();
		g1.id = goods_id.toString()
		g1.price = price;
		g1.mc_price = DirectComputePrice.compute(price_policy, film_no, film_dimen, new BigDecimal(price.toString()));
		g1.pay_amount = price;
		g1.total_amount = price;
		g1.discount = "0";
		totalAmount = MoneyUtil.add(totalAmount, g1.total_amount);
		goods_List.add(g1);
	}
	mcc.code = "0";
	mcc.offerAmount = '50'; // 优惠了的价格 原价100， 应付80 ， offerAmount=20
	mcc.items = goods_List;
	mcc.totalAmount = totalAmount;
	if ("use".equals(op)) {
		//当前是使用操作
		try {
			//CouponService.INSTANSE.use_by_order(codeIds.toString(),on,oc,pid);
		} catch (Exception e) {
			Logger.error("消费活动失败codeId=" + market.id.toString() , e);
		}
	}
} catch (Exception e) {
	Logger.error("活动价格计算失败", e);
	mcc.code = JsConstants.unknown;
	mcc.msg = "活动价格计算失败"
}

Logger.info('营销活动价格计算的返回结果：' + JackSonBeanMapper.toJson(mcc));
return  mcc;


