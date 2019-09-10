package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesChoise

def cho_id = JC.request.param('cho_id');

QuesChoise item = JcTemplate.INSTANCE().get(QuesChoise, 'select * from QuesChoise where flag!=? and id=?', -1, cho_id);
if(item==null) {
	return SimpleAjax.notAvailable('obj_not_found,项目答案未找到');
}

item.flag = -1;
item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
JcTemplate.INSTANCE().update(item);

return SimpleAjax.available();


