package com.jeancoder.market.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
class MarketRuleMemberCardRecharge {
	BigInteger id;
	BigInteger pid;
	String	mc_name;//活动名称
	String	mc_p_streg;//活动策略
	String	mc_l_ms//会员卡级别限制
	String	mc_l_f//限制首次优惠
	String	mc_l_u//充值次数限制
	String	mc_l_u_f//充值频率限制
	String	mc_l_u_v//具体充值频率值
	String	l_cal_t_n//充值总次数
	String	l_cal_t_a//充值总金额
	String	low_price_settle//是否第三方结算
	String	settle_aim//结算对象
	Date a_time;
	Timestamp c_time;
//	
//	DROP TABLE IF EXISTS `mm_market_rule_member_card_recharge`;
//	
//	CREATE TABLE `mm_market_rule_member_card_recharge` (
//	  `id` bigint(20) NOT NULL AUTO_INCREMENT,
//	  `pid` bigint(20) DEFAULT NULL,
//	  `mc_name` varchar(255) DEFAULT NULL,
//	  `mc_p_streg` varchar(255) DEFAULT NULL,
//	  `mc_l_ms` varchar(255) DEFAULT NULL,
//	  `mc_l_f` varchar(255) DEFAULT NULL,
//	  `mc_l_u` varchar(255) DEFAULT NULL,
//	  `mc_l_u_f` varchar(255) DEFAULT NULL,
//	  `mc_l_u_v` varchar(255) DEFAULT NULL,
//	  `l_cal_t_n` varchar(255) DEFAULT NULL,
//	  `l_cal_t_a` varchar(255) DEFAULT NULL,
//	  `low_price_settle` varchar(255) DEFAULT NULL,
//	  `settle_aim` varchar(255) DEFAULT NULL,
//	  `a_time` datetime DEFAULT NULL,
//	  `c_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
//	  PRIMARY KEY (`id`)
//	) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
}
