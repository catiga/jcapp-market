package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def item_id = JC.request.param('item_id');

QuesItem item = JcTemplate.INSTANCE().get(QuesItem, 'select * from QuesItem where flag!=? and id=?', -1, item_id);
if(item==null) {
	return SimpleAjax.notAvailable('obj_not_found,项目未找到');
}

item.flag = -1;
JcTemplate.INSTANCE().update(item);

return SimpleAjax.available();


