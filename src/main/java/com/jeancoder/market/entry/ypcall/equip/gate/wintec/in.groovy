package com.jeancoder.market.entry.ypcall.equip.gate.wintec

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.device.gate.wintec.DataObj
import com.jeancoder.market.ready.util.GeneralPub

/**
 * 客户过闸接口
 */
def eqno = JC.request.param('eqno');
def serial = JC.request.param('serial');
def eqs = JC.request.param('eqs');

DataObj doo = new DataObj();
doo.code = '0';
doo.msg = 'success';

if(!serial || serial.trim().equals('')) {
	doo.code = '-1';
	doo.msg = '参数错误：请传入入闸流水号';
	return GeneralPub.comfail('请检查流水号参数', doo);
}

SimpleAjax ticket_wrpper = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_code_for_direct_check', [get_code:serial, modify_status:'modify']);
if(ticket_wrpper==null) {
	doo.code = "401001";
	doo.msg = "请检查网络情况，验票失败";
	//return doo;
	return GeneralPub.comfail(doo);
}
if(!ticket_wrpper.available) {
	def err_code = ticket_wrpper.messages[0];
	def err_msg = err_code;
	if(ticket_wrpper.messages.length>1) {
		err_msg = ticket_wrpper.messages[1];
	}
	doo.code = err_code;
	doo.msg = err_msg;
	//return doo;
	return GeneralPub.comfail(doo);
}

return GeneralPub.success(doo);
