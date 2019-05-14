package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ma_movie_coupon_rule")
class MovieCouponRule {
	@JCID
	BigInteger id;  // id
	BigInteger batch_id;
	Date a_time;
	Timestamp c_time;
	
	String rule_value;// 规则策略 多个值用 / 分开；
	String time_value;// 时间策略
	String time_type;//  时间策略
	String quantitys_key;// 数量策略
	String quantitys_value;// 数量策略
	
	String stores; // 限制门店
	//影片类型限制 film type rule
	String movie_type; // 影片类型
	String cinema_type; //  影厅类型
	String halls; //  影厅id
	String movie;  // 限制影片
	Integer lowest;// 是否保护最低价 0 不保护，1 保护
}
