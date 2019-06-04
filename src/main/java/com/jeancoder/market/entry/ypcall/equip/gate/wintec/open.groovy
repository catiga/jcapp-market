package com.jeancoder.market.entry.ypcall.equip.gate.wintec

// code : 明文传入的扫码结果
// eqno : 设备编号
//1 开闸
//0 不开

import java.text.SimpleDateFormat;

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.device.gate.wintec.DataObj
import com.jeancoder.market.ready.device.gate.wintec.DeviceDto
import com.jeancoder.market.ready.device.gate.wintec.TicketInfoDto
import com.jeancoder.market.ready.util.GeneralPub

JCLogger logger = LoggerSource.getLogger();

def default_charset = "UTF-8";
def code = JC.request.param('code');	//二维码内容
def eqno = JC.request.param('eqno');	//设备编号
def type = JC.request.param('type');	//扫码类型

logger.info('scan ticket_code======' + code);

DataObj doo = new DataObj();
doo.code = "0";
doo.msg = "操作成功";

if(code==null) {
	doo.code = "201001";
	doo.msg = "缺少有效参数";
	//return doo;
	return GeneralPub.comfail(doo);
}

SimpleAjax ticket_wrpper = JC.internal.call(SimpleAjax, 'ticketingsys', '/ticketing/take_by_code_for_direct_check', [get_code:code, modify_status:'not_modify']);
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

def ticket = ticket_wrpper.data[0];
def seat = ticket_wrpper.data[1];
//检查票的场次是否已经过期
def plan_date = ticket['plan_date'];
def plan_time = ticket['plan_time'];
SimpleDateFormat _sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

try {
Date plan_real_time = _sdf.parse(plan_date + " " + plan_time);
Date d = new Date();
//开场时间减去当前时间要小于10分钟
//当前时间减去开场时间要小于5分钟

Long plan_real_time_long = plan_real_time.getTime();
Long d_long = d.getTime();

if((d_long - plan_real_time_long)>5*60*1000l) {
//	doo.code = "401005";
//	doo.msg = "对不起，检票时间已过，请联系值班经";
//	return GeneralPub.comfail(doo);
}

} catch(Exception e) {
e.printStackTrace();
	doo.code = "401001";
	//doo.msg = URLEncoder.encode("门票无效，验票失败", default_charset);
	doo.msg = "门票无效，验票失败";
	//return doo;
	return GeneralPub.comfail(doo);
}
def serial_obj = [:];
serial_obj['serial'] = ticket['id'] + ',' + seat['id'];		//返回[订单id_座位id]作为流水号给闸机
doo.detail = serial_obj;

return GeneralPub.success(doo);
