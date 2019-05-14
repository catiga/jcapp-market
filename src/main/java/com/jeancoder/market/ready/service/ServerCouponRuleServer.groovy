package com.jeancoder.market.ready.service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.CouponBatch
import com.jeancoder.market.ready.entity.MovieCouponRule
import com.jeancoder.market.ready.entity.ServerCouponRule

class ServerCouponRuleServer {
	private static final JCLogger LOGGER = LoggerSource.getLogger(ServerCouponRuleServer.class);
	static final ServerCouponRuleServer INSTANSE = new ServerCouponRuleServer();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();

	public  ServerCouponRule getRuleByBathId(BigInteger batch_id) {
		String sql = "select  * from ServerCouponRule where batch_id=?";
		return jcTemplate.get(ServerCouponRule.class, sql, batch_id);
	}

	public Integer updateItem(ServerCouponRule scr){
		ServerCouponRule item =getRuleByBathId(scr.batch_id);
		if(null==item){
			ServerCouponRule items = new ServerCouponRule();
			items.c_time = new Timestamp(new Date().getTime());
			items.batch_id = scr.batch_id;
			items.stores = scr.stores;
			items.server = scr.server;
			items.rule_value = scr.rule_value;
			items.time_type = scr.time_type;
			items.time_value = scr.time_value;
			items.quantitys_key = scr.quantitys_key;
			items.quantitys_value = scr.quantitys_value;
			return jcTemplate.save(items);
		}
		item.c_time = new Timestamp(new Date().getTime());
		item.stores = scr.stores;
		item.server = scr.server;
		item.rule_value = scr.rule_value;
		item.time_type = scr.time_type;
		item.time_value = scr.time_value;
		item.quantitys_key = scr.quantitys_key;
		item.quantitys_value = scr.quantitys_value;
		return jcTemplate.update(item);
	}
	
	public String [] dealString(String s){
		String [] ss = s.split("/");
		return ss;
	}
}
