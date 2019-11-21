package com.jeancoder.market.entry.market

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.util.GlobalHolder

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

String mt = JC.request.param("mt");

Result result = new Result();
result.setView("market/add/add_" + mt);

def pid = GlobalHolder.proj.id;
def tyc = '1000';
//获取系统配置支付方式
SimpleAjax allow_pts = JC.internal.call(SimpleAjax, 'project', '/incall/project_pts', [pid:pid, tyc:tyc]);
def pay_method_results = [];
if(allow_pts && allow_pts.available) {
	pay_method_results = allow_pts.data;
}
result.addObject('allow_pts', pay_method_results);

return result;
