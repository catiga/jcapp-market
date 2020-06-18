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

def sql = 'select * from FigureInfo where flag!=? and pid=?';
def params = []; params.add(-1);
if(!pid) {
	pid = -1;
}
params.add(pid);

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


