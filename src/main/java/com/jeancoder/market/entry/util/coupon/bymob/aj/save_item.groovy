package com.jeancoder.market.entry.util.coupon.bymob.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.entity.CbPubItem
import com.jeancoder.market.ready.util.DataUtils
import com.jeancoder.market.ready.util.GlobalHolder

def mob = JC.request.param('mob');
def num = JC.request.param('num');
def cb = JC.request.param('cb');
def item_id = JC.request.param('item_id');

def pubid = JC.request.param('pubid');

if(!mob || !num || !cb) {
	return SimpleAjax.notAvailable('param_error,参数错误');
}
try {
	num = Integer.valueOf(num);
} catch(any) {
	return SimpleAjax.notAvailable('param_error,发券数量需输入数字');
}

if(mob.length()!=11) {
	return SimpleAjax.notAvailable('param_error,请检查手机号码');
}

def pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and pid=?', pubid, GlobalHolder.proj.id);
if(!pub) {
	return SimpleAjax.notAvailable('obj_not_found,请先保存券基本信息');
}

if(pub.ss!='0000') {
	return SimpleAjax.notAvailable('status_invalid,状态非法');
}

CbPubItem item = null;
if(item_id&&item_id!='0') {
	item = JcTemplate.INSTANCE().get(CbPubItem, 'select * from CbPubItem where id=? and flag!=?', item_id, -1);
	if(!item) {
		return SimpleAjax.notAvailable('obj_not_found,发券信息已删除');
	}
}

if(item) {
	//走更新流程
	item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	item.mobile = mob;
	item.num = num;
	JcTemplate.INSTANCE().update(item);
} else {
	//判断mob是否已经被添加
	def sql = 'select * from CbPubItem where mobile=? and pubid=? and flag!=? and cb=?';
	
	def result = JcTemplate.INSTANCE().find(CbPubItem, sql, mob, pubid, -1, cb);
	if(result) {
		return SimpleAjax.notAvailable('obj_repeat,手机号已发券勿重复操作');
	}
	
	item = new CbPubItem();
	item.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	item.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	item.flag = 0;
	item.mobile = mob;
	try {
		item.cb = new BigInteger(cb);
	}catch(any){
		return SimpleAjax.notAvailable('param_error,请选择正确的券批次');
	}
	item.num = num;
	item.pubid = pub.id;
	item.id = JcTemplate.INSTANCE().save(item);
}

return SimpleAjax.available('', item);




