CREATE TABLE `ma_figure_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `picture` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `flag` bigint(2) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL,
  `url_msg` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `pid` bigint(2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `figure_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
