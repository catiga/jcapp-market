package com.jeancoder.market.entry.util.coupon.bymob.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.CbPubInfo
import com.jeancoder.market.ready.entity.CbPubItem
import com.jeancoder.market.ready.util.DataUtils
import com.jeancoder.market.ready.util.GlobalHolder

def item_id = JC.request.param('item_id');

def pubid = JC.request.param('pubid');

def pub = JcTemplate.INSTANCE().get(CbPubInfo, 'select * from CbPubInfo where id=? and pid=?', pubid, GlobalHolder.proj.id);
if(!pub) {
	return SimpleAjax.notAvailable('obj_not_found,请先保存券基本信息');
}

if(pub.ss!='0000') {
	return SimpleAjax.notAvailable('status_invalid,状态非法');
}

CbPubItem item = JcTemplate.INSTANCE().get(CbPubItem, 'select * from CbPubItem where id=? and flag!=?', item_id, -1);
if(!item) {
	return SimpleAjax.notAvailable('obj_not_found,发券信息已删除');
}

item.flag = -1;
JcTemplate.INSTANCE().update(item);
return SimpleAjax.available();




