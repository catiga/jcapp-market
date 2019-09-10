package com.jeancoder.market.entry.util.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def pn = JC.request.param('pn');
def ps = JC.request.param('ps');

ps = 20;

try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}
JcPage<QuesPack> page = new JcPage<QuesPack>();
page.pn = pn;
page.ps = ps;

page = JcTemplate.INSTANCE().find(QuesPack, page, 'select * from QuesPack where flag!=? and pid=?', -1, GlobalHolder.proj.id);

Result result = new Result();
result.setView('util/questionnaire/index');

result.addObject('page', page);

return result;
