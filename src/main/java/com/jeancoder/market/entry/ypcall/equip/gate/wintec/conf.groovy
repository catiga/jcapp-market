package com.jeancoder.market.entry.ypcall.equip.gate.wintec

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.device.gate.wintec.GateConf
import com.jeancoder.market.ready.device.gate.wintec.GateInfo
import com.jeancoder.market.ready.util.GeneralPub

def eqno = JC.request.param('eqno');		//设备编号

def dev_no = eqno;

GateConf gate = new GateConf();


if(dev_no==null||dev_no.trim().equals("")) {
    gate.code = "201001";
    gate.msg = "参数缺少或无效";

    return gate;
}

def eq_info = new GateInfo();
eq_info.eq_pos = 1;
eq_info.eq_name = dev_no + '号闸机';
eq_info.eq_no = dev_no;

gate.code = "0";
gate.msg = "success";
gate.eq_info = eq_info;

//return gate;

return GeneralPub.success(gate);
