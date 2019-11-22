package com.jeancoder.market.internal.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.MarketRuleTcss

def mobile = JC.internal.param('mobile');

def pid = JC.internal.param('pid');
def ap_id = JC.internal.param('ap_id');
def order_no = JC.internal.param('order_no');
def order_oc = JC.internal.param('order_oc');

//这里直接选择营销活动，需要过滤一下手机号
def sql = 'select * from MarketRuleTcss where flag!=? and market_id in (select id from MarketInfo where flag!=? and mtype=? and pid=?)';

List<MarketRuleTcss> rules = JcTemplate.INSTANCE().find(MarketRuleTcss, sql, -1, -1, order_oc, pid);

if(!rules || rules.empty) {
	return SimpleAjax.available();
}

