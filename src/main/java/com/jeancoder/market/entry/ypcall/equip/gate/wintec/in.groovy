package com.jeancoder.market.entry.ypcall.equip.gate.wintec

import com.jeancoder.app.sdk.JC
import com.jeancoder.market.ready.device.gate.wintec.DataObj
import com.jeancoder.market.ready.util.GeneralPub

def eqno = JC.request.param('eqno');
def serial = JC.request.param('serial');
def eqs = JC.request.param('eqs');

DataObj doo = new DataObj();
doo.code = '0';
doo.msg = 'success';

return GeneralPub.success(doo);
