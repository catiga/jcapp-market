CREATE TABLE `cbpub_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `info` varchar(1024) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pid` bigint(20) DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `ouid` bigint(20) DEFAULT NULL,
  `ouname` varchar(255) DEFAULT NULL,
  `ss` char(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `cbpub_item` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pubid` bigint(20) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `cb` bigint(20) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  `ccid` bigint(20) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;