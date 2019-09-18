package com.jeancoder.market.internal.questionnaire

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesChoise
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.entity.QuesResult

/**
 * 
 */
def pid = JC.internal.param('pid');
def qp_id = JC.internal.param('qp_id');
def ap_id = JC.internal.param('ap_id');

def sql = 'select * from QuesPack where flag!=? and pid=? and id=?';

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, sql, -1, pid, qp_id);
if(pack==null) {
	return SimpleAjax.notAvailable('pack_not_found');
}

sql = 'select * from QuesItem where flag!=? and pack=?';
List<QuesItem> items = JcTemplate.INSTANCE().find(QuesItem, sql, -1, pack.id);

//查找对应的所有问题的选项
sql = 'select * from QuesChoise where flag!=? and item_id in (select id from QuesItem where pack=?)';
List<QuesChoise> choises = JcTemplate.INSTANCE().find(QuesChoise, sql, -1, pack.id);

for(x in items) {
	def aim_choises = [];
	for(y in choises) {
		if(x.id==y.item_id) {
			aim_choises.add(y);
		}
	}
	x.choises = aim_choises;
}
pack.items = items;



List<QuesResult> result = null;
if(ap_id) {
	//传入了ap id，则需要查找当前这个用户已经回答过的此问卷的结果
	sql = 'select * from QuesResult where flag!=? and pack_id=? and basic_id=?';
	result = JcTemplate.INSTANCE().find(QuesResult, sql, -1, pack.id, ap_id);
}

return SimpleAjax.available('', [pack, result]);

