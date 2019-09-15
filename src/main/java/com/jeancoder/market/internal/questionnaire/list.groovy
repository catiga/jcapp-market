package com.jeancoder.market.internal.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesPack

def pid = JC.internal.param('pid');

def sql = 'select * from QuesPack where flag!=? and pid=?';

List<QuesPack> result = JcTemplate.INSTANCE().find(QuesPack, sql, -1, pid);

return SimpleAjax.available('', result);
