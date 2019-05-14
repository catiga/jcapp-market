package com.jeancoder.market.entry.util.coupon.byrule

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.util.GlobalHolder

def pn = 1;
try {
	pn = Integer.valueOf(JC.request.param('pn'));
} catch(any) {}

JcPage<CbPubInfo> page = new JcPage<CbPubInfo>();
page.pn = pn;
page.ps = 20;

def sql = 'select * from CbPubInfo where flag!=? and pid=? and cbpt=? order by a_time desc';

page = JcTemplate.INSTANCE().find(CbPubInfo, page, sql, -1, GlobalHolder.proj.id, '0000');

Result result = new Result();
result.addObject('page', page);

result.setView("util/coupon/byrule/index");
return result;

