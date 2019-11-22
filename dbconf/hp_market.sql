/*
 Navicat MySQL Data Transfer

 Source Server         : 10.3.66.160
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 10.3.66.160:3306
 Source Schema         : hp_market

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 28/04/2019 22:16:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cbpub_info
-- ----------------------------
DROP TABLE IF EXISTS `cbpub_info`;
CREATE TABLE `cbpub_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `pid` bigint(20) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `ouid` bigint(20) NULL DEFAULT NULL,
  `ouname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ss` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cbpt` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for cbpub_item
-- ----------------------------
DROP TABLE IF EXISTS `cbpub_item`;
CREATE TABLE `cbpub_item`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pubid` bigint(20) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cb` bigint(20) NULL DEFAULT NULL,
  `num` int(11) NULL DEFAULT NULL,
  `ccid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_coupon_batch
-- ----------------------------
DROP TABLE IF EXISTS `ma_coupon_batch`;
CREATE TABLE `ma_coupon_batch`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `coupon_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `rule` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'p_lowest:1保护最低限价2不保护。',
  `total` int(11) NOT NULL DEFAULT 0,
  `available` int(11) NOT NULL DEFAULT 0,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `coupon_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1000',
  `pid` bigint(20) NOT NULL,
  `validate_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `validate_period` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `is_outer` tinyint(4) NOT NULL DEFAULT 0,
  `outer_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_pc_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `outer_pc_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cb_level` int(11) NOT NULL DEFAULT 1,
  `s_id` bigint(20) NULL DEFAULT NULL,
  `use_count` int(11) NULL DEFAULT NULL,
  `use_count_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `use_con` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '10',
  `cbstatus` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '21',
  `crapp` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cbunifycode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cbunifyenable` tinyint(4) NULL DEFAULT NULL,
  `cucs` tinyint(4) NULL DEFAULT NULL,
  `cuvs` tinyint(4) NULL DEFAULT NULL,
  `version` tinyint(4) NULL DEFAULT NULL,
  `wxcard_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `coupon_no_unique`(`coupon_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ma_coupon_batch
-- ----------------------------
INSERT INTO `ma_coupon_batch` VALUES (8, 'sp120190428490', 'vip票', NULL, NULL, NULL, '2019-04-28 18:09:14', NULL, 1, 1, '2019-04-28 18:09:14', 0, '2000', 1, '0000', '0000', 0, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '10', '20', '2000', NULL, 0, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for ma_coupon_code
-- ----------------------------
DROP TABLE IF EXISTS `ma_coupon_code`;
CREATE TABLE `ma_coupon_code`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account_project_id` bigint(20) NULL DEFAULT NULL,
  `takeby` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始领用人',
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原始领用人的获取途径',
  `get_time` datetime(0) NULL DEFAULT NULL,
  `use_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `use_admin_id` bigint(11) NULL DEFAULT 0,
  `use_admin_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `use_type` tinyint(4) NULL DEFAULT NULL,
  `a_time` datetime(0) NOT NULL,
  `validate_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `validate_period` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ma_coupon_code
-- ----------------------------
INSERT INTO `ma_coupon_code` VALUES (39, 8, '6NZfiq', '00', NULL, NULL, NULL, NULL, NULL, NULL, '2019-04-28 18:17:38', 0, 0, NULL, 1, '2019-04-28 18:17:38', '0000', '0000');

-- ----------------------------
-- Table structure for ma_coupon_trans
-- ----------------------------
DROP TABLE IF EXISTS `ma_coupon_trans`;
CREATE TABLE `ma_coupon_trans`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cb_id` bigint(20) NULL DEFAULT NULL,
  `cc_id` bigint(20) NULL DEFAULT NULL,
  `cc_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `get_partid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `from_partid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` timestamp(0) NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_goods_coupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `ma_goods_coupon_rule`;
CREATE TABLE `ma_goods_coupon_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `batch_id` bigint(11) UNSIGNED NOT NULL,
  `stores` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '限制门店',
  `goods` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '限制商品',
  `rule_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则',
  `time_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略类型',
  `time_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略',
  `quantitys_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略类型',
  `quantitys_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of ma_goods_coupon_rule
-- ----------------------------
INSERT INTO `ma_goods_coupon_rule` VALUES (1, '2019-04-24 18:24:24', '2019-04-24 18:24:24', 2, '', '', '1/1/0', '0', NULL, '100', '');
INSERT INTO `ma_goods_coupon_rule` VALUES (2, '2019-04-25 16:50:19', '2019-04-25 16:50:19', 3, '', '', '1/1/0', '0', NULL, '100', '');
INSERT INTO `ma_goods_coupon_rule` VALUES (3, '2019-04-25 17:01:31', '2019-04-25 17:01:31', 4, '', '', '3000', '0', NULL, '100', '');

-- ----------------------------
-- Table structure for ma_json_rule_with_mc
-- ----------------------------
DROP TABLE IF EXISTS `ma_json_rule_with_mc`;
CREATE TABLE `ma_json_rule_with_mc`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `mc_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_ht` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_sw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_r` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_plays` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_playe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_pds` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_pde` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_pay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cwro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `low_price_settle` bigint(11) NULL DEFAULT NULL,
  `clear_handle_fee_policy` bigint(11) NULL DEFAULT NULL,
  `l_ms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_t_n` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_t_nw` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cal_t_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `settle_aim` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_pts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_p_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_user_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_v` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_market_info
-- ----------------------------
DROP TABLE IF EXISTS `ma_market_info`;
CREATE TABLE `ma_market_info` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `dnum` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  `content` text,
  `mtype` varchar(4) DEFAULT NULL,
  `mstatus` char(2) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `cg` int(11) DEFAULT NULL,
  `cgu` bigint(20) DEFAULT NULL,
  `selectmode` tinyint(4) NOT NULL DEFAULT '1',
  `dgid` varchar(255) DEFAULT NULL,
  `mru_id` bigint(255) DEFAULT NULL,
  `limit_user` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ma_movie_coupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `ma_movie_coupon_rule`;
CREATE TABLE `ma_movie_coupon_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `batch_id` bigint(11) UNSIGNED NOT NULL,
  `cucs` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '影片类型限制',
  `cuvs` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '影片类型限制',
  `ticsum` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '影片类型限制',
  `poucut` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '影片类型限制',
  `movie_type` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '影片类型',
  `cinema_type` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT ' 影厅类型',
  `stores` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '限制影城',
  `movie` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT ' 限制影片',
  `lowest` tinyint(4) NOT NULL COMMENT '是否选择保护最低价',
  `rule_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则',
  `time_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略类型',
  `time_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略',
  `quantitys_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略类型',
  `quantitys_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略',
  `halls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_order_coupon
-- ----------------------------
DROP TABLE IF EXISTS `ma_order_coupon`;
CREATE TABLE `ma_order_coupon`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `cbid` bigint(20) NULL DEFAULT NULL,
  `oc` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cash_dd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `order_item_id` bigint(20) NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `offer_amount` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_server_coupon_rule
-- ----------------------------
DROP TABLE IF EXISTS `ma_server_coupon_rule`;
CREATE TABLE `ma_server_coupon_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `batch_id` bigint(11) UNSIGNED NOT NULL,
  `stores` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '限制门店',
  `server` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '限制商品',
  `rule_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '规则',
  `time_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略类型',
  `time_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略',
  `quantitys_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略类型',
  `quantitys_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数量策略',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ma_service_pack
-- ----------------------------
DROP TABLE IF EXISTS `ma_service_pack`;
CREATE TABLE `ma_service_pack`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `spno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `spname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `spinfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `spcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `spin` tinyint(4) NOT NULL DEFAULT 1,
  `imgurl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `chargetype` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '10',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mm_market_rule
-- ----------------------------
DROP TABLE IF EXISTS `mm_market_rule`;
CREATE TABLE `mm_market_rule`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `pid` bigint(255) NULL DEFAULT NULL,
  `mc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_p_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_ms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_v` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cal_t_n` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cal_t_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `low_price_settle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `settle_aim` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mm_market_rule_member_card_recharge
-- ----------------------------
DROP TABLE IF EXISTS `mm_market_rule_member_card_recharge`;
CREATE TABLE `mm_market_rule_member_card_recharge`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `mc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_p_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_ms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_f` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_v` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cal_t_n` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `l_cal_t_a` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `low_price_settle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `settle_aim` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mm_market_rule_recharge
-- ----------------------------
DROP TABLE IF EXISTS `mm_market_rule_recharge`;
CREATE TABLE `mm_market_rule_recharge`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(255) NULL DEFAULT NULL,
  `mc_p_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_ms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_ms_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for mm_market_rule_ticketingsys
-- ----------------------------
DROP TABLE IF EXISTS `mm_market_rule_ticketingsys`;
CREATE TABLE `mm_market_rule_ticketingsys`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `index_id` bigint(20) NULL DEFAULT NULL,
  `market_info_id` bigint(20) NOT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  `mc_rule_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desi_movie` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `spru_time_spec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_p_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_f` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mc_l_u_v` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_mc_rule` int(16) NULL DEFAULT NULL,
  `member_card_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `member_card_rule_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `desi_movie_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票务营销活动子表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_project_print_setup
-- ----------------------------
DROP TABLE IF EXISTS `sys_project_print_setup`;
CREATE TABLE `sys_project_print_setup`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `setup_type` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `proj_id` bigint(20) NULL DEFAULT NULL,
  `setup_json` varchar(10254) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_project_print_setup
-- ----------------------------

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
  
  `choise_id` bigint(20) DEFAULT NULL,
  `choise_no` varchar(255) DEFAULT NULL,
  `choise_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21319 DEFAULT CHARSET=utf8;

CREATE TABLE `mm_market_rule_tcss` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `market_id` bigint(20) DEFAULT NULL,
  `store_id` bigint(20) DEFAULT NULL,
  `store_name` varchar(255) DEFAULT NULL,
  `limit_movies` varchar(1024) DEFAULT NULL,
  `limit_halls` varchar(1024) DEFAULT NULL,
  `limit_pay_types` varchar(1024) DEFAULT NULL,
  `plan_start_date` char(10) DEFAULT NULL,
  `plan_start_time` char(8) DEFAULT NULL,
  `plan_end_date` char(10) DEFAULT NULL,
  `plan_end_time` char(8) DEFAULT NULL,
  `price_policy` varchar(1024) DEFAULT NULL,
  `number_policy` varchar(1024) DEFAULT NULL,
  `bind_pay_account` tinyint(4) DEFAULT '0',
  `first_buy_eff` tinyint(4) DEFAULT '0',
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `low_price_settle` tinyint(4) DEFAULT '0',
  `settle_price` decimal(10,2) DEFAULT NULL,
  `settle_obj` varchar(255) DEFAULT NULL,
  `handle_fee` decimal(10,2) DEFAULT NULL,
  `join_type` char(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


INSERT INTO `sys_project_print_setup` VALUES (2, '0002', 1, '{\"height\":\"80\",\"width\":\"180\",\"nodes\":[{\"font_size\":12,\"value_prefix\":\"\",\"top\":10,\"left\":9,\"node_name\":\"ticket_no_qrcode\",\"height\":\"20\",\"width\":\"20\",\"rotate\":\"\"},{\"font_size\":12,\"value_prefix\":\"地址：花果山景区五圣广场\",\"top\":38,\"left\":12,\"node_name\":\"manual\",\"height\":\"\",\"width\":\"\",\"rotate\":90},{\"font_size\":12,\"value_prefix\":\"vip票\",\"top\":37,\"left\":25,\"node_name\":\"manual\",\"height\":\"\",\"width\":\"\",\"rotate\":90},{\"font_size\":12,\"value_prefix\":\"5排12号\",\"top\":38,\"left\":17,\"node_name\":\"manual\",\"height\":\"\",\"width\":\"\",\"rotate\":90},{\"font_size\":18,\"value_prefix\":\"\",\"top\":165,\"left\":28,\"node_name\":\"coupon_code\",\"height\":\"\",\"width\":\"\",\"rotate\":\"\"}]}', '2019-04-23 17:11:43', '2019-04-28 19:05:26', 0);

SET FOREIGN_KEY_CHECKS = 1;
