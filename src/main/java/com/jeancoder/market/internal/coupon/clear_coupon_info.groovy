package com.jeancoder.market.internal.coupon

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.common.SimpleAjax
import com.jeancoder.market.ready.entity.MarketMobileBuy
import com.jeancoder.market.ready.entity.MarketMobileLimit
import com.jeancoder.market.ready.entity.OrderCoupon
import com.jeancoder.market.ready.service.OrderCouponServer
import com.jeancoder.market.ready.util.JackSonBeanMapper
import com.jeancoder.market.ready.util.StringUtil

Result result = new Result();
JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
def p = JC.internal.param("orders");
def pid = JC.internal.param("pid");
Logger.info("orders__" + p)

if (StringUtil.isEmpty(p)||StringUtil.isEmpty(pid)) {
	return SimpleAjax.notAvailable('参数不可为空');
}
List<OrderCoupon> resultList = new ArrayList();

String [] list = p.split(";"); //参数为："编号,oc"

def a_time = Calendar.getInstance().getTime();
def time_diff = 15*60*1000L;	//15分钟
try {
	for (int i = 0 ; i<list.length;i++) {
		def item = list[i].split(",");
		OrderCoupon order = new OrderCoupon();
		order.order_no = item[0];
		order.oc = item[1];
		order.pid = new BigInteger(pid);
		List<OrderCoupon> result_data  = OrderCouponServer.INSTANSE.update_order_coupon(order);
		resultList.addAll(result_data);
		
		//修改并还原手机号对应的订单信息
		def sql = 'select * from MarketMobileBuy where flag!=? and order_no=? and oc=? order by a_time desc limit 1, 10';
		try {
			List<MarketMobileBuy> mobile_markets = JcTemplate.INSTANCE().find(MarketMobileBuy, sql, -1, item[0], item[1]);
			if(mobile_markets) {
				for(x in mobile_markets) {
					if((a_time.getTime() - x.a_time.getTime())<=time_diff) {
						//小于15分钟，直接重置还原
						x.flag = -1;
						JcTemplate.INSTANCE().update(x);
					}
				}
			}
		} catch(any) {
			Logger.error("restore_market_rule_fail:" + item[0], any);
		}
	}
} catch (Exception e) {
	Logger.error("restore_coupon_fail:", e);
	//return SimpleAjax.notAvailable("修改卡券状态失败");
}

return SimpleAjax.available("",resultList);