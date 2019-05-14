package com.jeancoder.market.ready.service
//
//import com.jeancoder.app.sdk.source.LoggerSource
//import com.jeancoder.core.log.JCLogger
//import com.jeancoder.jdbc.JcTemplate
//import com.jeancoder.market.ready.entity.CouponCode
//
//class find_avaliable_goods_coupon {
//	
//	private static final JCLogger LOGGER = LoggerSource.getLogger(find_avaliable_goods_coupon.class);
//	static final GoodsCouponRuleServer INSTANSE = new GoodsCouponRuleServer();
//	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
//	
//	CouponService cs = CouponService.INSTANSE;
//	
//	public List<CouponCode> getCouponCodes (String mobile,String sid,def goods){
//		List<CouponCode> ccList = cs.get_codes_by_mobile(mobile);
//		cs.is_Available_goods(ccList, sid, goods);
//	}
//}
