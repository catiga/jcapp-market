package com.jeancoder.market.entry.util.coupon.bymob.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.util.GlobalHolder

def pubid = JC.request.param('pubid');
def title = JC.request.param('title');
def info = JC.request.param('info');

def update = false;
def pub = null;
if(pubid&&pubid!='0') {
	pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and flag!=?', pubid, -1);
	if(!pub) {
		return SimpleAjax.notAvailable('obj_not_found,信息未找到');
	}
	update = true;
} else {
	pub = new CbPubInfo();
	pub.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	pub.flag = 0;
	pub.pid = GlobalHolder.proj.id;
	pub.ss = '0000';
	pub.cbpt = '0000';
	pub.ouid = GlobalHolder.token.user.id;
	pub.ouname = GlobalHolder.token.user.username;
}
if(!title) {
	return SimpleAjax.notAvailable('param_error,请输入主题信息');
}

pub.title = title;
pub.info = info;
if(update) {
	JcTemplate.INSTANCE().update(pub);
} else {
	pub.id = JcTemplate.INSTANCE().save(pub);
}
return SimpleAjax.available('', pub);

