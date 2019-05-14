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