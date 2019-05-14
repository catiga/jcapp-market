package com.jeancoder.market.ready.dto.coupon

class CouponCodeDto {
	BigInteger id;
	String code;
	String coupon_type; //代金劵1000  兑换2000   折扣3000
	String crapp;// 商品劵1000  2000选座  5000服务劵
	//coupon_type = 1000  rule_value=5000     rule_value 代金金额：5000分
	//coupon_type = 2000  rule_value=1/2/300   1张卡劵可以兑换2个商品或者影片 需要补300 分
	//coupon_type = 3000   rule_value=80/100   打8折，最高可折扣 100分
	String rule_value;// 规则如下 
	String[] rule; // 规则
	String batch_title; //名称
	String item_id;
	String validate_type;// 有效期类型    0010   0000 1001
	Long validate_end;// 有效期截止时间的时间戳
}
