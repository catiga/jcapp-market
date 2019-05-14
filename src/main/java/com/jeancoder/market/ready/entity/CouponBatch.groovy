package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn


@JCBean(tbname = "ma_coupon_batch")
class CouponBatch {

	@JCID
	BigInteger id;  // id
	
	String coupon_no;

	String coupon_type;

	String title;

	String info;

	String content;

	String img;

	Date a_time = new Date();

	//初始值 0
	Integer total;
	
	//初始值 0
	Integer available;

	Timestamp c_time;

	//为0表示不可用  初始值 0
	Integer flag;

	//项目标志
	BigInteger pid;

	/**
	 * add field
	 * 有效期类型：
	 * 0000：永久有效；
	 * 0010：统一时间失效
	 * 1001：按天结束
	 * 1002：按月结束
	 * 1003：按年结束
	 * 初始值： 0000
	 */
	String validate_type;
	/**
	 * 初始值：0000
	 * validateType的辅助字段
	 * 0000:0，表示永久有效
	 * 1001:1，表示一天后结束
	 * 1002:1，表示一月后结束
	 * 1003:1，表示一年后结束
	 * 初始值：0
	 */
	String validate_period;

	/** add new field by 20161212 **/
	//初始值：0
	Integer is_outer;
	String outer_type;
	String outer_uri;
	String outer_pc_num;
	String outer_pc_key;

	//默认是项目级 1
	Integer cb_level;

	//商店id
	BigInteger s_id;

	/** add new field by 20161214 **/
	//卡券可用数量
	Integer use_count;
	//卡券可用数量条件，by每件商品，by每类商品，by每个订单
	String use_count_by;

	//是否可与优惠活动叠加，为空或默认情况下是不可共用   初始值：10
	String use_con;

	//卡券状态，停用或可用   初始值21
	String cbstatus;

	//券应用类型
	String crapp;

	//券批次编码
	String cbunifycode;
	//是否开启统一领用，默认不允许    初始值 0
	Integer cbunifyenable;
	@JCNotColumn
	def couponRule;
	
	String wxcard_id;
}

