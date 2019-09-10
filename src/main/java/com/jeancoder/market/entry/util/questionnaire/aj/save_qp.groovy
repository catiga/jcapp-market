package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');
def qp_no = JC.request.param('qp_no');
def qp_name = JC.request.param('qp_name');
def qp_info = JC.request.param('qp_info');

QuesPack pack = null;
if(qp_id && qp_id!='0') {
	pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);
	if(pack==null) {
		return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
	}
}
def update = true;
if(pack==null) {
	update = false;
	pack = new QuesPack();
	pack.flag = 0;
	pack.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	pack.pid = GlobalHolder.proj.id;
}

pack.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
pack.pack_info = qp_info;
pack.pack_name = qp_name;
pack.pack_no = qp_no;

if(update) {
	JcTemplate.INSTANCE().update(pack);
} else {
	JcTemplate.INSTANCE().save(pack);
}

return SimpleAjax.available();

