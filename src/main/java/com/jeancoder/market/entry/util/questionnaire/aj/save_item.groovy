package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');
def item_id = JC.request.param('item_id');
def item_name = JC.request.param('item_name');
def item_no = JC.request.param('item_no');
def item_subhead = JC.request.param('item_subhead');
def item_qt = JC.request.param('item_qt');

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);
if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

QuesItem item = null;
if(item_id && item_id!='0') {
	item = JcTemplate.INSTANCE().get(QuesItem, 'select * from QuesItem where flag!=? and pack=? and id=?', -1, pack.id, item_id);
	if(item==null) {
		return SimpleAjax.notAvailable('obj_not_found,项目未找到');
	}
}
def update = true;
if(item==null) {
	update = false;
	item = new QuesItem();
	item.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	item.flag = 0;
	item.pack = pack.id;
}

item.itemno = item_no;
item.question = item_name;
item.subhead = item_subhead;
item.qt = item_qt;

if(update) {
	JcTemplate.INSTANCE().update(item);
} else {
	JcTemplate.INSTANCE().save(item);
}

return SimpleAjax.available();


