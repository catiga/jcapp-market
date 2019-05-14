CREATE TABLE `ma_coupon_trans` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `cb_id` bigint(20) DEFAULT NULL,
  `cc_id` bigint(20) DEFAULT NULL,
  `cc_code` varchar(255) DEFAULT NULL,
  `get_partid` varchar(255) DEFAULT NULL,
  `from_partid` varchar(255) DEFAULT NULL,
  `a_time` timestamp NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;