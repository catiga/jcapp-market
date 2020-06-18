package com.jeancoder.market.internal.figure.admin

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.FigureInfo

def id = JC.internal.param('id');
def pid = JC.internal.param('pid');

def sql = 'select * from FigureInfo where pid=? and id=?';

FigureInfo figure = JcTemplate.INSTANCE().get(FigureInfo, sql, pid, id);

if(figure!=null) {
	figure.flag = -1;
	JcTemplate.INSTANCE().update(figure);
}

return SimpleAjax.available();
