package com.jeancoder.market.internal.figure.admin

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.FigureInfo

def pid = JC.internal.param('pid');
def pn = JC.internal.param('pn');
def ps = JC.internal.param('ps');
//以后考虑接受其他筛选参数
//增加figure_type参数
def figure_type = JC.internal.param('figure_type');

//增加order by 参数
def order_by_field = JC.internal.param('obf');
def order_by_seq = JC.internal.param('obs');

def sql = 'select * from FigureInfo where flag!=? and pid=?';
def params = []; params.add(-1);
if(!pid) {
	pid = -1;
}
params.add(pid);
if(figure_type) {
	sql = sql + ' and figure_type=?';
	params.add(figure_type);
}

if(order_by_field && order_by_seq && (order_by_seq.toString().toLowerCase()=='asc' || order_by_seq.toString().toLowerCase()=='desc')) {
	sql = sql + ' order by ' + order_by_field + ' ' + order_by_seq;
}

try {
	pn = Integer.valueOf(pn);
} catch(ay) {
}

try {
	ps = Integer.valueOf(ps);
} catch(any) {
}

if(!pn || pn<1) {
	pn = 1;
}
if(!ps || ps<1) {
	ps = 1;
}

JcPage<FigureInfo> page = new JcPage<FigureInfo>();
page.pn = pn;
page.ps = ps;

page = JcTemplate.INSTANCE().find(FigureInfo, page, sql, params.toArray());

return SimpleAjax.available('', page);


