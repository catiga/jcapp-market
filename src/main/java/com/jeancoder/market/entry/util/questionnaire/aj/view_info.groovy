package com.jeancoder.market.entry.util.questionnaire.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);

if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

return SimpleAjax.available('', pack);
