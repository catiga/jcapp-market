package com.jeancoder.market.entry.util.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

JCLogger logger = JCLoggerFactory.getLogger('qp_items');

def qp_id = JC.request.param('qp_id');

logger.info('qp_id=' + qp_id + ',' + GlobalHolder.proj.id);

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);

def items = JcTemplate.INSTANCE().find(QuesItem, 'select * from QuesItem where flag!=? and pack=?', -1, pack.id);

Result result = new Result();
result.setView('util/questionnaire/items');

result.addObject('pack', pack);
result.addObject('items', items);

return result;
