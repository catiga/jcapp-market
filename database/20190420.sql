CREATE TABLE `sys_project_print_setup` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `setup_type` char(4) NOT NULL DEFAULT '',
  `proj_id` bigint(20) DEFAULT NULL,
  `setup_json` varchar(10254) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;