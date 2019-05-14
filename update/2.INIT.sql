/*记录卡劵使用情况*/
CREATE TABLE `ma_order_coupon` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) DEFAULT NULL,
  `order_no` varchar(255) DEFAULT '',
  `cbid` bigint(20) DEFAULT NULL,
  `oc` char(16) DEFAULT NULL,
  `cash_dd` varchar(255) DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `order_item_id` bigint(20) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `offer_amount` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `mm_market_rule_ticketingsys` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `index_id` bigint(20) DEFAULT NULL,
  `market_info_id` bigint(20) NOT NULL,
  `flag` int(2) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `mc_rule_name` varchar(255) DEFAULT NULL,
  `desi_movie` varchar(255) DEFAULT NULL,
  `spru_time_spec` varchar(255) DEFAULT NULL,
  `mc_p_streg` varchar(255) DEFAULT NULL,
  `mc_l_u` varchar(16) DEFAULT NULL,
  `mc_l_u_f` varchar(20) DEFAULT NULL,
  `mc_l_u_v` varchar(20) DEFAULT NULL,
  `is_mc_rule` int(16) DEFAULT NULL,
  `member_card_rule` varchar(255) DEFAULT NULL,
  `member_card_rule_name` varchar(255) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `desi_movie_name` varchar(255) DEFAULT NULL,
  `hall_id` varchar(255) DEFAULT NULL,
  `store_id` varchar(255) DEFAULT NULL,
  `time_type` varchar(20) DEFAULT NULL,
  `film_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='票务营销活动子表';

/*2019-03-07 在线服务预约卡劵字表*/
CREATE TABLE `ma_goods_coupon_rule` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `a_time` datetime NOT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `batch_id` bigint(11) unsigned NOT NULL,
  `stores` varchar(225) NOT NULL DEFAULT '' COMMENT '限制门店',
  `goods` varchar(225) NOT NULL DEFAULT '' COMMENT '限制商品',
  `rule_value` varchar(255) DEFAULT NULL COMMENT '规则',
  `time_type` varchar(255) DEFAULT NULL COMMENT '时间策略类型',
  `time_value` varchar(255) DEFAULT NULL COMMENT '时间策略',
  `quantitys_key` varchar(255) DEFAULT NULL COMMENT '数量策略类型',
  `quantitys_value` varchar(255) DEFAULT NULL COMMENT '数量策略',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
