package com.jeancoder.market.entry.util.questionnaire.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def item_id = JC.request.param('item_id');

QuesItem pack = JcTemplate.INSTANCE().get(QuesItem, 'select * from QuesItem where id=? and flag!=?', item_id, -1);

if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

return SimpleAjax.available('', pack);
