package com.jeancoder.market.entry.util.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);


Result result = new Result();
result.setView('util/questionnaire/preview');

def qp_items = null;

if(pack!=null) {
	qp_items = JcTemplate.INSTANCE().find(QuesItem, 'select * from QuesItem where flag!=? and pack=?', -1, pack.id);
}

result.addObject('pack', pack);
result.addObject('qp_items', qp_items);

return result;

