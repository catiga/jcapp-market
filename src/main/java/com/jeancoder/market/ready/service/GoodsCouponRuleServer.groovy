package com.jeancoder.market.ready.service

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.market.ready.entity.GoodsCouponRule;

class GoodsCouponRuleServer {
	private static final JCLogger LOGGER = LoggerSource.getLogger(GoodsCouponRuleServer.class);
	static final GoodsCouponRuleServer INSTANSE = new GoodsCouponRuleServer();
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	/**
	 *
	 * @param batch_id
	 * @param stores
	 * @return
	 */
	public Integer findAvaliableRule(BigInteger batch_id,String stores){
		String sql ="select * from GoodsCouponRule where batch_id =? and stores = ? or stores is null";
		GoodsCouponRule gcr = jcTemplate.get(GoodsCouponRule.class, sql, batch_id,stores);
		if(gcr==null){
			return -1;
		}
		return 1;
	}
	
	
	
	public  GoodsCouponRule getRule_ByBathId(BigInteger batch_id) {
		String sql = "select  * from GoodsCouponRule where batch_id=? ";
		return jcTemplate.get(GoodsCouponRule.class, sql, batch_id);
	}
	public Integer updateItem(GoodsCouponRule goods){
		GoodsCouponRule goodsRule=getRule_ByBathId(goods.batch_id);
		boolean flag = false;
		if(goodsRule==null){
			goodsRule = new GoodsCouponRule();
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
			return jcTemplate.save(goodsRule);
		}else{
			return jcTemplate.update(goodsRule);
		}
	}
}
