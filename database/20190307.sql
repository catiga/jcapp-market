CREATE TABLE `ma_online_server_coupon_rule` (
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;