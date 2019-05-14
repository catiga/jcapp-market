package com.jeancoder.market.entry.ypcall.equip.gate.wintec

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.device.gate.wintec.DataObj
import com.jeancoder.market.ready.util.GeneralPub

// eqno : 设备编号
def eqno = JC.request.param('eqno');		//设备编号
def eqs = JC.request.param('eqs');			//设备状态

DataObj gate = new DataObj();

gate.code = "0";
gate.msg = "success";

return GeneralPub.success(gate);
