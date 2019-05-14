package com.jeancoder.market.ready.service


import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.GoodsCouponRule
import com.jeancoder.market.ready.entity.OnlineServerCouponRule

class OnlineServerCouponRuleService {

	private static final JCLogger LOGGER = LoggerSource.getLogger(OnlineServerCouponRuleService.class);
	
	static final OnlineServerCouponRuleService INSTANSE = new OnlineServerCouponRuleService();
	
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	
	public  OnlineServerCouponRule getRule_ByBathId(BigInteger batch_id) {
		String sql = "select  * from OnlineServerCouponRule where batch_id=? ";
		return jcTemplate.get(OnlineServerCouponRule.class, sql, batch_id);
	}
	
	
	public  void updateItem(OnlineServerCouponRule goods) {
		OnlineServerCouponRule goodsRule = getRule_ByBathId(goods.batch_id);
		boolean flag = false;
		if(goodsRule == null){
			goodsRule = new OnlineServerCouponRule();
			flag = true;
			goodsRule.a_time = new Date();
			goodsRule.batch_id = goods.batch_id;
		}
		goodsRule.goods = goods.goods;
		goodsRule.stores=goods.stores;
		goodsRule.c_time=goods.c_time;
		goodsRule.rule_value=goods.rule_value;
		goodsRule.time_type=goods.time_type;
		goodsRule.time_value=goods.time_value;
		goodsRule.quantitys_key=goods.quantitys_key;
		goodsRule.quantitys_value=goods.quantitys_value;
		if(flag==true){
			 jcTemplate.save(goodsRule);
		}else{
			 jcTemplate.update(goodsRule);
		}
	}
	
	
	
}
