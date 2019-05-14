package com.jeancoder.market.ready.entity

import java.util.List

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.market.ready.dto.KV
import com.jeancoder.market.ready.dto.MarketJsonRule

@JCBean(tbname = "ma_json_rule_with_mc")
class MarketJsonRuleWithMC {
	@JCID
	BigInteger id;//主键 

	//会员卡等级列表
	String mc_list;

	String l_cs;//限制影城

	String l_cts;//限制城市

	String l_ht;//限制影厅

	String cal_sw;//最低票价票补设置

	String cal_a;//结算金额

	String cal_r;//第三方票补信息

	//放映时间段开始
	String l_plays;
	//放映时间段结束
	String l_playe;
	//放映开始日期
	String l_pds;
	//放映结束日期
	String l_pde;
	//是否限制支付账户
	String  l_pay;

	String l_cwro = "0";//是否锁座

	//默认非最低票价结算
	Integer low_price_settle = 0;

	//默认收取手续费
	Integer clear_handle_fee_policy = 0;


	String l_ms;//限制影片

	String l_f;// 是否首单优惠

	String cal_t_n;//投放限制张数

	String cal_t_nw;//投票限制模式

	String cal_t_a;//投放金额
	//结算对象
	String settle_aim;
	//限制支付方式
	String l_pts;
	
	String mc_p_streg//价格策略
	
	String mc_user_list//是否限制用户列表参加
	
	String mc_l_u//具体购票频率值
	
	String mc_l_u_f//购票频率限制
	
	String mc_l_u_v//具体购票频率值
}
