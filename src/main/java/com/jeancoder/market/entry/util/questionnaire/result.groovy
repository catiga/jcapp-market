package com.jeancoder.market.entry.util.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.entity.QuesResult
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');
def pn = JC.request.param('pn');
try {
	pn = Integer.valueOf(pn);
	if(pn<1) {
		pn = 1;
	}
} catch(any) {
	pn = 1;
}

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);

JcPage<QuesResult> page = new JcPage<QuesResult>();
page.pn = pn;
page.ps = 20;

page = JcTemplate.INSTANCE().find(QuesResult, page, 'select * from QuesResult where flag!=? and pack_id=?', -1, pack.id);

Result view = new Result();
view.setView('util/questionnaire/result');
view.addObject('page', page);
view.addObject('pack', pack);

return view;