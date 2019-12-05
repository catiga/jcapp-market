package com.jeancoder.market.ready.util

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory

class DirectComputePrice {

	static JCLogger logger = JCLoggerFactory.getLogger('compute price service:');
	
	static final BigDecimal _100_ = new BigDecimal(100);
	
	static final BigDecimal _10_ = new BigDecimal(10);
	
	/**
	 * 20/2d:15;20/3d:25
	 * 00/99
	 * 
	 * 00:销售立减
	 * 10:折扣
	 * 20:固定价格
	 * 50:最低票价减
	 */
	def static compute(String price_policy, String film_no, String film_dimen, BigDecimal seat_price) {
		String[] arr_price_policy = price_policy.split(';');
		//开始顺序匹配规则
		BigDecimal final_price = seat_price;
		BigDecimal price_pol = null;
		String price_type = null;
		for(x in arr_price_policy) {
			String[] arr_x = x.split('\\/');
			String[] real_pol = arr_x[1].split('\\:');
			if(real_pol.length>1) {
				//说明设置了2d或者3d规则
				String type_pol = real_pol[0];
				
				if(type_pol!='00') {
					//区分了2d或者3d价格
					if(film_dimen.toLowerCase().indexOf(type_pol.toLowerCase())>-1) {
						//说明匹配到了
						price_type = arr_x[0];
						price_pol = new BigDecimal(real_pol[1]);
						break;
					}
				}
			} else {
				price_pol = new BigDecimal(real_pol[0]);
				price_type = arr_x[0];
			}
		}
		if(price_type!=null && price_pol!=null) {
			if(price_type=='00') {
				//销售立减
				final_price = seat_price.subtract(price_pol.multiply(_100_));
			} else if(price_type=='10') {
				//折扣
				final_price = seat_price.multiply(price_pol).divide(_10_);
			} else if(price_type=='20') {
				//固定价格
				final_price = price_pol.multiply(_100_);
			}
		} else {
			//匹配规则失败
			logger.info('match price failed. ex original price');
		}
		return final_price;
	}
}
