package com.jeancoder.market.internal.questionnaire

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesChoise
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.entity.QuesResuItem
import com.jeancoder.market.ready.entity.QuesResult
import com.jeancoder.market.ready.util.JackSonBeanMapper

def pid = JC.internal.param('pid');
def ap_id = JC.internal.param('ap_id');

def qp_id = JC.internal.param('qp_id');

def answers = JC.internal.param('answers');

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where flag!=? and id=? and pid=?', -1, qp_id, pid);
if(pack==null) {
	return SimpleAjax.notAvailable('pack_not_found,问卷未找到');
}
try {
	answers = JackSonBeanMapper.jsonToList(answers);
}catch(any) {
	answers = null;
}
if(!answers) {
	return SimpleAjax.notAvailable('result_empty,请回答问卷问题');
}

def a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());

//立刻生成一份pack result记录
QuesResult pack_result = new QuesResult();
pack_result.a_time = a_time;
pack_result.basic_id = ap_id;
pack_result.c_time = a_time;
pack_result.flag = 0;
pack_result.pack_id = pack.id;
pack_result.id = JcTemplate.INSTANCE().save(pack_result);

//获取pack items
def sql = 'select * from QuesItem where flag!=? and pack=?';
List<QuesItem> items = JcTemplate.INSTANCE().find(QuesItem, sql, -1, pack.id);

for(x in items) {
	def qt = x.qt;
	def item_id = x.id;
	List<QuesResuItem> result_item = [];
	for(y in answers) {
		if(y['item_id'].toString()==item_id.toString()) {
			QuesResuItem item = new QuesResuItem();
			item.basic_id = ap_id;
			item.result = y['item_answer'].toString();
			if(qt!='10') {
				try {
					def choise_id = new BigInteger(y['item_answer'].toString());
					QuesChoise choise = JcTemplate.INSTANCE().get(QuesChoise, "select * from QuesChoise where id=?", choise_id);
					if(choise==null) {
						continue;
					}
					item.choise_id = choise_id;
					item.choise_no = choise.awno;
					item.choise_name = choise.awname;
				}catch(any) {}
			}
			item.a_time = a_time;
			item.flag = 0;
			item.item_id = item_id;
			item.pack_id = pack.id;
			item.qt = qt;
			item.resu_id = pack_result.id;
			item.c_time = a_time;
			JcTemplate.INSTANCE().save(item);
		}
	}
}

return SimpleAjax.available();

