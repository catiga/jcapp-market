package com.jeancoder.market.entry.util.questionnaire.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesChoise
import com.jeancoder.market.ready.entity.QuesItem

def item_id = JC.request.param('item_id');

QuesItem item = JcTemplate.INSTANCE().get(QuesItem, 'select * from QuesItem where flag!=? and id=?', -1, item_id);
if(item==null) {
	return SimpleAjax.notAvailable('obj_not_found,项目未找到');
}


def result = JcTemplate.INSTANCE().find(QuesChoise, 'select * from QuesChoise where flag!=? and item_id=?', -1, item.id);

return SimpleAjax.available('', result);