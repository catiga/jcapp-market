package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketMobileBuy
import com.jeancoder.market.ready.entity.MarketMobileLimit
import com.jeancoder.market.ready.entity.MarketRuleTcss
import com.jeancoder.market.ready.util.TotalDateUtil

def mobile = JC.internal.param('mobile');

def pid = JC.internal.param('pid');
def ap_id = JC.internal.param('ap_id');
def order_no = JC.internal.param('order_no');
def market_rule_id = JC.internal.param('market_rule_id');
def tnum = JC.internal.param('tnum');
def market_id = JC.internal.param('market_id');

//先写死选座的类型
def sql = 'select * from MarketRuleTcss where flag!=? and id=? and market_id=?';
MarketRuleTcss rule = JcTemplate.INSTANCE().get(MarketRuleTcss, sql, -1, market_rule_id, market_id);
if(rule==null) {
	return SimpleAjax.notAvailable('obj_not_found,活动规则未找到');
}

MarketInfo market = JcTemplate.INSTANCE().get(MarketInfo, 'select * from MarketInfo where flag!=? and id=? and pid=?', -1, rule.market_id, pid);
if(market==null) {
	return SimpleAjax.notAvailable('obj_not_found,活动已经结束');
}

def limit_user = market.limit_user;
if(limit_user==1) {
	if(mobile==null) {
		return SimpleAjax.notAvailable('mobile_empty,请传入手机号');
	}
	//查找当前用户是否在这个目录里面
	List<MarketMobileLimit> mobiles = JcTemplate.INSTANCE().find(MarketMobileLimit, 'select * from MarketMobileLimit where flag!=? and market_id=? and mobile=?', -1, market.id, mobile);
	if(mobiles==null || mobiles.empty) {
		return SimpleAjax.notAvailable('not_in_list,手机号不在允许的优惠名单');
	}
}

/**
 * 20/2d:15;20/3d:25
 * 00/99
 */
def price_policy = rule.price_policy;

/**
 * 2,m,
 * 8,-1,	//活动哪总数量
 * 3,w,3,5	//按照
 */
def num_policy = rule.number_policy;
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
	return SimpleAjax.notAvailable('repeat_join_forbid,当前周期内参与次数超限', mobile_buy_list);
}

return SimpleAjax.available('', (mobile_buy_list.size() - 1));


