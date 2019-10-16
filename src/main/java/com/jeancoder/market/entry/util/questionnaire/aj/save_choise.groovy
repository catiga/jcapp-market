package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesChoise
import com.jeancoder.market.ready.entity.QuesItem

def item_id = JC.request.param('item_id');
def cho_id = JC.request.param('cho_id');

def cho_name = JC.request.param('cho_name');
def cho_no = JC.request.param('cho_no');
def need_text = JC.request.param('need_text');
def cho_vise = JC.request.param('cho_vise');

QuesItem item = JcTemplate.INSTANCE().get(QuesItem, 'select * from QuesItem where flag!=? and id=?', -1, item_id);
if(item==null) {
	return SimpleAjax.notAvailable('obj_not_found,项目未找到');
}

QuesChoise choise = null;
if(cho_id && cho_id!='0') {
	choise = JcTemplate.INSTANCE().get(QuesChoise, 'select * from QuesChoise where flag!=? and id=?', -1, cho_id);
	if(choise==null) {
		return SimpleAjax.notAvailable('obj_not_found,选项未找到');
	}
}
def update = true;
if(choise==null) {
	update = false;
	choise = new QuesChoise();
	choise.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	choise.flag = 0;
	choise.item_id = item.id;
}

choise.awname = cho_name;
choise.awno = cho_no;

choise.awvise = cho_vise;
try {
	choise.input_falg = Integer.valueOf(need_text);
}catch(any) {
	choise.input_falg = 0;
}

choise.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());

if(update) {
	JcTemplate.INSTANCE().update(choise);
} else {
	choise.id = JcTemplate.INSTANCE().save(choise);
}

return SimpleAjax.available('', choise.id);







