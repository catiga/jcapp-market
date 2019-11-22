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
def sql = 'select a.*, b.title, b.info from MarketRuleTcss a, MarketInfo b where a.flag!=? and a.market_id=b.id and b.flag!=? and b.mtype=? and b.pid=? and b.mstatus=? order by c_time desc';

List<Object> rules = JcTemplate.INSTANCE().find(Object, sql, -1, -1, order_oc, pid, '20');

if(!rules || rules.empty) {
	return SimpleAjax.available();
}

return SimpleAjax.available('', rules);
