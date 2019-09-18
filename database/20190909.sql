CREATE TABLE `data_tp_quespack` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `pack_no` varchar(255) DEFAULT NULL,
  `pack_name` varchar(255) DEFAULT NULL,
  `pack_info` varchar(2048) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `useflag` tinyint(4) NOT NULL DEFAULT '1',
  `sgutimes` int(11) NOT NULL DEFAULT '0',
  `tplname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `data_tp_quesitem` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pack` bigint(20) DEFAULT NULL,
  `itemno` varchar(255) DEFAULT NULL,
  `question` varchar(2048) DEFAULT NULL,
  `subhead` varchar(2048) DEFAULT NULL,
  `qt` varchar(16) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1059 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `data_tp_queschoise` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) DEFAULT NULL,
  `awno` varchar(255) DEFAULT NULL,
  `awname` varchar(255) DEFAULT NULL,
  `awvise` varchar(255) DEFAULT NULL,
  `input_falg` tinyint(4) NOT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5498 DEFAULT CHARSET=utf8mb4;








CREATE TABLE `data_tp_quesresu` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pack_id` bigint(20) DEFAULT NULL,
  `basic_id` bigint(20) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=752 DEFAULT CHARSET=utf8;

CREATE TABLE `data_tp_quesresuitem` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pack_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `basic_id` bigint(20) DEFAULT NULL,
  `resu_id` bigint(20) DEFAULT NULL,
  `result` text,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21319 DEFAULT CHARSET=utf8;


alter table data_tp_quesresuitem add column `choise_id` bigint(20) DEFAULT NULL;
alter table data_tp_quesresuitem add column `choise_no` varchar(255) DEFAULT NULL;
alter table data_tp_quesresuitem add column `choise_name` varchar(255) DEFAULT NULL;

