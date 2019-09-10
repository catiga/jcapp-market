package com.jeancoder.market.entry.util.questionnaire.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.QuesItem
import com.jeancoder.market.ready.entity.QuesPack
import com.jeancoder.market.ready.entity.QuesResuItem
import com.jeancoder.market.ready.entity.QuesResult
import com.jeancoder.market.ready.util.GlobalHolder

def qp_id = JC.request.param('qp_id');
def resu_id = JC.request.param('resu_id');

QuesPack pack = JcTemplate.INSTANCE().get(QuesPack, 'select * from QuesPack where id=? and flag!=? and pid=?', qp_id, -1, GlobalHolder.proj.id);

if(pack==null) {
	return SimpleAjax.notAvailable('obj_not_found,问卷未找到');
}

List<QuesResuItem> ques_result = JcTemplate.INSTANCE().find(QuesResuItem, 'select a.*, b.qt, b.question from QuesResuItem a left join QuesItem b on a.item_id=b.id where a.flag!=? and a.pack_id=? and a.resu_id=?', -1, pack.id, resu_id);

def items = '';
if(ques_result) {
	for(x in ques_result) {
		def result_qt = x.qt;
		if(result_qt=='10') result_qt = '文本';
		else if(result_qt=='21') result_qt = '单选';
		else if(result_qt=='22') result_qt = '多选';
		def result_item = """
			<div class="row">
				<div class="col-md-12 margin-bottom-15">
					<label for="qp_info" class="control-label">${x.question} （${result_qt}）</label>
					<span class="form-control">${x.result}</span>
				</div>
			</div>
		""";
		items += result_item;
	}
}

def result_html = """
	<div id="templatemo-preferences-form-fast" style="padding-left:15px;padding-right:15px">
		${items}
	</div>
"""
return SimpleAjax.available('', result_html.toString());
