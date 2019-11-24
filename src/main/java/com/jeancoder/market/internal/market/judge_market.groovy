package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.MarketInfo
import com.jeancoder.market.ready.entity.MarketMobileLimit
import com.jeancoder.market.ready.entity.MarketRuleTcss

def mobile = JC.internal.param('mobile');

def pid = JC.internal.param('pid');
def ap_id = JC.internal.param('ap_id');
def order_no = JC.internal.param('order_no');
def market_rule_id = JC.internal.param('market_rule_id');
def tnum = JC.internal.param('tnum');

//先写死选座的类型
def sql = 'select * from MarketRuleTcss where flag!=? and id=?';
MarketRuleTcss rule = JcTemplate.INSTANCE().get(MarketRuleTcss, sql, -1, market_rule_id);
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

return SimpleAjax.available();


