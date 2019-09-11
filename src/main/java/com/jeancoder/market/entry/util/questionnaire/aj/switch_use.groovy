package com.jeancoder.market.entry.util.questionnaire.aj

import java.sql.Timestamp

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

if(pack.useflag==0) {
	pack.useflag = 1;
} else {
	pack.useflag = 0;
}
pack.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
JcTemplate.INSTANCE().update(pack);

return SimpleAjax.available();
