package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.OrderCoupon
import com.jeancoder.market.ready.service.OrderCouponServer
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName())
def p = JC.internal.param("orders");
def pid = JC.internal.param("pid");
Logger.info("orders__" + p)
try {
	if (StringUtil.isEmpty(p)||StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable('参数不可为空');
	}
	List<OrderCoupon> resultList = new ArrayList();
	String [] list = p.split(";");//参数为："编号+oc"
	for (int i = 0 ; i<list.length;i++) {
		def item = list[i].split(",");
		OrderCoupon order = new OrderCoupon();
		order.order_no = item[0];
		order.oc = item[1];
		order.pid = new BigInteger(pid);
		List<OrderCoupon> result_data  = OrderCouponServer.INSTANSE.update_order_coupon(order);
		resultList.addAll(result_data);
	}
	Logger.info('coupon_data=' + JackSonBeanMapper.listToJson(resultList) );
	return SimpleAjax.available("",resultList);
} catch (Exception e) {
	Logger.error("修改卡券状态失败", e);
	return SimpleAjax.notAvailable("修改卡券状态失败");
}