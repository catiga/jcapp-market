package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.constant.JsConstants
import com.jeancoder.market.ready.constant.MarketConstants
import com.jeancoder.market.ready.dto.MarketJsonRule
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketRuleTcss
import com.jeancoder.market.ready.service.MarketRuleService
import com.jeancoder.market.ready.util.DateUtil
import com.jeancoder.market.ready.util.RemoteUtil

JCLogger logger = JCLoggerFactory.getLogger(this.getClass().getName());

String mc_type = JC.request.param("mc_type");//1
String mc_name = JC.request.param("mc_name");//活动名称 1
String mc_no = JC.request.param("mc_no");//编号1
String mc_info = JC.request.param("mc_info");//活动描述1
String mc_p_streg = JC.request.param("mc_p_streg");//价格策略
String mc_start_time = JC.request.param("mc_start_time");//活动开始时间1
String mc_end_time = JC.request.param("mc_end_time");//活动结束时间1
String mc_user_list = JC.request.param("mc_user_list");//是否限制用户列表参加
String mc_l_ms = JC.request.param("mc_l_ms");//限制影片value 1
String mc_l_cs = JC.request.param("mc_l_cs");//限制影城value 1
String mc_l_pt = JC.request.param("mc_l_pt");//限制支付方式1
String mc_l_u = JC.request.param("mc_l_u");//具体购票频率值
String mc_l_u_f = JC.request.param("mc_l_u_f");//购票频率限制
String mc_l_u_v = JC.request.param("mc_l_u_v");//具体购票频率值
String mc_content = JC.request.param("mc_content");//营销活动详细信息1
String mc_l_f = JC.request.param("mc_l_f");//限制首单优惠1
String mc_l_pay = JC.request.param("mc_l_pay");//限制支付账户1
String mc_l_cts = JC.request.param("mc_l_cts");//限制城市 1
String l_cal_t_n = JC.request.param("l_cal_t_n");//投放限制张数1
String l_cal_t_a = JC.request.param("l_cal_t_a");//投放金额1
String l_cal_sw = JC.request.param("l_cal_sw");//最低票价票补设置 1
String l_cal_a = JC.request.param("l_cal_a");//结算金额1
String l_cal_r = JC.request.param("l_cal_r");//第三方票补信息1
String mc_l_ht = JC.request.param("mc_l_ht");//限制影厅value  1
String low_price_settle = JC.request.param("low_price_settle");//是否最低票价结算1
String settle_aim = JC.request.param("settle_aim");//结算对象 1
String clear_handle_fee_policy = JC.request.param("clear_handle_fee_policy");//手续费政策1
String l_cal_t_nw = JC.request.param("l_cal_t_nw");//投票限制模式1
String l_pds = JC.request.param("l_pds");//放映开始日期1
String l_pde = JC.request.param("l_pde");//放映结束日期1
String l_plays = JC.request.param("l_plays");//放映时间段开始1
String l_playe = JC.request.param("l_playe");//放映时间段结束1
String mc_l_cwro = JC.request.param("mc_l_cwro");//是否锁座1

BigInteger pid = RemoteUtil.getProj().getId();

//参数处理
if(!(low_price_settle.equals("1")||low_price_settle.equals("0"))) {
	//return AvailabilityStatus.notAvailable(JsConstants.input_param_error);
	return SimpleAjax.notAvailable(JsConstants.input_param_error + ',请选择是否最低票价结算')
}
if(!(clear_handle_fee_policy.equals("0")||clear_handle_fee_policy.equals("1"))) {
	return SimpleAjax.notAvailable(JsConstants.input_param_error + ',请选择是否收取手续费')
}
Date start_time = null, end_time = null;

start_time = DateUtil.getDate(mc_start_time,"yyyy-MM-dd HH:mm:ss");
end_time = DateUtil.getDate(mc_end_time,"yyyy-MM-dd HH:mm:ss");
if(start_time.after(end_time)) {
	return SimpleAjax.notAvailable(JsConstants.input_param_error + ',开始时间不能大于结束时间')
}
if(start_time==null||end_time==null) {
	return SimpleAjax.notAvailable(JsConstants.input_param_error + ',请设置活动开始和结束时间')
}

MarketInfo mkInfo = new MarketInfo();
mkInfo.mtype = mc_type;
mkInfo.title = mc_name;
mkInfo.dnum = mc_no==""?generate_coupon_no(pid):mc_no;
mkInfo.info = mc_info;
mkInfo.start_time = start_time;
mkInfo.end_time = end_time;
mkInfo.content = mc_content;
mkInfo.a_time = Calendar.getInstance().getTime();
mkInfo.pid = pid;
mkInfo.mstatus = MarketConstants._market_status_wait_;
//首先保存活动主表
mkInfo.id = JcTemplate.INSTANCE().save(mkInfo);

//保存规则逻辑
List<MarketRuleTcss> real_rules = [];

def a_time = Calendar.getInstance().getTime();
if(mc_l_ms && mc_l_ms!='') {
	//设置了限制的影城信息，需要生成多条
	def mc_l_ms_arr = mc_l_ms.split(',');
	for(x in mc_l_ms_arr) {
		MarketRuleTcss rule = new MarketRuleTcss();
		rule.market_id = mkInfo.id;
		rule.store_id = new BigInteger(x);
		rule.store_name = '稍后去project查！';
		rule.a_time = a_time;
		rule.flag = 0;
		real_rules.add(rule);
	}
} else {
	MarketRuleTcss rule = new MarketRuleTcss();
	rule.market_id = mkInfo.id;
	rule.a_time = a_time;
	rule.flag = 0;
	real_rules.add(rule);
}

if(!real_rules) {
	return SimpleAjax.notAvailable('baby_empty,规则关键信息为空 请检查选择的门店参数');
}
//开始将所有规则数据设置进去
for(x in real_rules) {
	x.bind_pay_account = mc_l_pay=='1' ? 1 : 0;
	x.first_buy_eff = mc_l_f&&mc_l_f=='1'? 1 : 0;
	x.limit_halls = mc_l_ht;
	x.limit_movies = mc_l_cs;
	x.limit_pay_types = mc_l_pt;
	x.number_policy = mc_l_u + ',' + mc_l_u_f + ',' + mc_l_u_v;
	x.plan_end_date = l_pde;
	x.plan_end_time = l_playe;
	x.plan_start_date = l_pds;
	x.plan_start_time = l_plays;
	x.price_policy = mc_p_streg;
	x.low_price_settle = low_price_settle&&low_price_settle=='1'? 1 : 0;
	x.settle_price = l_cal_a?new BigDecimal(l_cal_a) : 0;
	x.settle_obj = settle_aim;
	x.handle_fee = clear_handle_fee_policy?new BigDecimal(clear_handle_fee_policy):0;
	x.join_type = mc_l_cwro;
}

//开始保存数据

try{
	for(x in real_rules) {
		JcTemplate.INSTANCE().save(x);
	}
	return SimpleAjax.available();
}catch(Exception e){
	logger.error("",e);
	return SimpleAjax.notAvailable('create_fail,活动创建或更新失败');
}

String generate_coupon_no(def pid) {
	String buffer = "yx" + pid.toString() + DateUtil.format_yyyyMMdd(new Date()) + this.nextInt(100, 999);
	buffer = buffer.replaceAll("\\-", "");
	return buffer;
}

int nextInt(final int min, final int max){
	Random rand= new Random();
	int tmp = Math.abs(rand.nextInt());
	return tmp % (max - min + 1) + min;
}
