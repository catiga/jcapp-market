package com.jeancoder.market.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.constant.CouponConstants
import com.jeancoder.market.ready.entity.CouponCode
import com.jeancoder.market.ready.entity.OrderCoupon
import com.jeancoder.market.ready.util.StringUtil
import com.jeancoder.jdbc.JcPage

class OrderCouponServer {

	private static final JCLogger LOGGER = LoggerSource.getLogger(OrderCouponServer.class);
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	static final OrderCouponServer INSTANSE = new OrderCouponServer();


	public void create_order_counpon(List<CouponCode> list, String on, String oc,BigInteger pid) {
		Timestamp c_time = new Timestamp(new Date().getTime());
		for (CouponCode code : list) {
			OrderCoupon order = new OrderCoupon();
			order.pid = pid;
			order.order_no = on;
			order.oc = oc;
			order.cbid = code.id;
			order.c_time = c_time;
			jcTemplate.save(order);
		}
	}
	public List<OrderCoupon> getItems(def pid,def order_no,def oc){
		String sql = "select * from OrderCoupon where pid =? and order_no =? and oc=?  and flag!=?";
		return jcTemplate.find(OrderCoupon, sql, pid,order_no,oc,-1);
	}
	
	public List<OrderCoupon> update_order_coupon(def order){
		List<OrderCoupon> items = getItems(order.pid, order.order_no, order.oc);
		List<OrderCoupon> list = new ArrayList<OrderCoupon>();
		if (items!=null && !items.isEmpty() ) {
			for (OrderCoupon o : items) {
				o.flag = -1;
				o.c_time = new Timestamp(new Date().getTime());
				jcTemplate.update(o);
				list.add(o)
				CouponCode code = CouponService.INSTANSE.get_codes_by_id(o.cbid);//将对应的卡券初始化
				code.status = CouponConstants._coupon_code_status_touse_ ;//将卡券修改为分配 
				code.use_time = null;
				code.c_time = new Timestamp(new Date().getTime());
				jcTemplate.update(code);
			}
		}
		return list;
	}
	
	
	public JcPage<OrderCoupon> getPage(JcPage<OrderCoupon> page,def key, def pid){
		String sql ="select order_c.* from OrderCoupon order_c , CouponCode code where code.id=order_c.cbid  AND order_c.flag!=?  and order_c.pid=?";
		if (!StringUtil.isEmpty(key)) {
			sql += " and ( code ='"+key +"' or order_no='"+key+"' or mobile='"+key+"' )";
		}
		return jcTemplate.find(OrderCoupon, page, sql, -1,pid);
	}
}