/*
 Navicat Premium Data Transfer

 Source Server         : tencent-database
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : 175.24.185.242:3306
 Source Schema         : medusa

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 04/07/2023 18:06:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for solar_base_info
-- ----------------------------
DROP TABLE IF EXISTS `solar_base_info`;
CREATE TABLE `solar_base_info` (
  `b_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
  `nickname` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像',
  `gender` int DEFAULT NULL COMMENT '性别 0 男 1 女',
  `birth` date DEFAULT NULL COMMENT '出生年月',
  `u_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`b_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_base_info
-- ----------------------------
BEGIN;
INSERT INTO `solar_base_info` (`b_id`, `nickname`, `avatar`, `gender`, `birth`, `u_id`) VALUES ('1670800030887440386', '哈哈哈哈', '123', 0, '1992-08-11', '1670800027569745922');
INSERT INTO `solar_base_info` (`b_id`, `nickname`, `avatar`, `gender`, `birth`, `u_id`) VALUES ('1670801131871182849', '莫杨', '123', 1, NULL, '1670801128859672578');
INSERT INTO `solar_base_info` (`b_id`, `nickname`, `avatar`, `gender`, `birth`, `u_id`) VALUES ('1671080568986550273', NULL, NULL, NULL, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for solar_menu
-- ----------------------------
DROP TABLE IF EXISTS `solar_menu`;
CREATE TABLE `solar_menu` (
  `m_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
  `menu_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单名称',
  `router_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '路由名称',
  `router_link` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '路由链接',
  `component_address` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '组件地址',
  `menu_level` int DEFAULT NULL COMMENT '级别',
  `sort` int DEFAULT NULL COMMENT '排序',
  `perm` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '权限字符',
  `icon` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '图标',
  `group_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '菜单集id',
  `parent_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父级菜单',
  `hidden` int DEFAULT NULL COMMENT '0 不隐藏 1 隐藏',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_role`;
CREATE TABLE `solar_role` (
  `r_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
  `role_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_role
-- ----------------------------
BEGIN;
INSERT INTO `solar_role` (`r_id`, `role_name`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1668549228361736193', 'test1', '2023-06-13 17:21:51', NULL, '2023-06-14 15:34:23', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_role_router
-- ----------------------------
DROP TABLE IF EXISTS `solar_role_router`;
CREATE TABLE `solar_role_router` (
  `role_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色id',
  `router_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '路由id',
  UNIQUE KEY `granted_menu` (`role_id`,`router_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_role_router
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_secret
-- ----------------------------
DROP TABLE IF EXISTS `solar_secret`;
CREATE TABLE `solar_secret` (
  `s_id` varchar(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '秘钥主键',
  `secret_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '秘钥',
  `secret_salt` char(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '盐',
  `secret_expired_time` datetime DEFAULT NULL COMMENT '秘钥过期时间',
  `u_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_secret
-- ----------------------------
BEGIN;
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1', '{bcrypt}$2a$10$t9nqM/.z7l7ukuGQEB/bFuWn.lk8InstgIhUmVK9gqr0ybMUJnDA6', '123456', '2023-01-29 18:59:13', '1');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1669281592163487746', '{bcrypt}$2a$10$j9qGfOiKP54rwdq2k1dtfeFO6eSgFp7ESexQfVQ3zymSqXGdCTHFq', NULL, NULL, '1669281573784047618');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1669641636390371330', '{bcrypt}$2a$10$CUY.1ajdnqKXWq2q0T7XheNfXmmPqgu8K2tAR.Eh61yfCDBIp2NgC', NULL, NULL, '1669641635366961154');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1669642114415296513', '{bcrypt}$2a$10$HPzBncRABVzJTKsAam3wUOISNLhBjZdohWZtrwavadUx.3Dbu4QD2', NULL, NULL, '1669642113316388865');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1669643853096816642', '{bcrypt}$2a$10$4/87rdZNJQ0ogfWFUGcUUuwPcv3Z.F43iELONyFpxuIQ22NSB/qWe', NULL, NULL, '1669643852002103298');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1670801130151518209', '{bcrypt}$2a$10$XJ1KTDYsZL3KifEfUEvGle45.fcs3pZc5Lu77C1chfKotjmbJwaFW', NULL, NULL, '1670801128859672578');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1671076782788354050', '{bcrypt}$2a$10$SzB2HnSyHt.MY3Jei2UsFOOUFYAbJIhhbs5rtOxitCbi9iX1oUjZ2', NULL, NULL, '1671076781706223617');
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`) VALUES ('1671076878414290945', '{bcrypt}$2a$10$32sBw1eN1XC6Yb5yl/f9gOdzPS2Bj.Yx01Ez0YKJ57mFiHu8O7xzm', NULL, NULL, '1671076877206331394');
COMMIT;

-- ----------------------------
-- Table structure for solar_user
-- ----------------------------
DROP TABLE IF EXISTS `solar_user`;
CREATE TABLE `solar_user` (
  `u_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户全局唯一id',
  `locked` int unsigned DEFAULT '0' COMMENT '是否锁定 0 没有 1 被锁定',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_user
-- ----------------------------
BEGIN;
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', 0, NULL, '2022-12-30 18:57:55', 'default', '2023-06-20 16:53:48', NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1669281573784047618', 0, NULL, '2023-06-15 17:51:56', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1669641635366961154', 0, NULL, '2023-06-16 17:42:41', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1669642113316388865', 0, NULL, '2023-06-16 17:44:35', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1669643852002103298', 0, NULL, '2023-06-16 17:51:29', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1670800027569745922', 0, '2023-07-15 12:00:00', '2023-06-19 22:25:43', NULL, '2023-06-20 17:32:41', NULL, NULL, 1);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1670801128859672578', 0, '2016-02-17 15:35:31', '2023-06-19 22:30:06', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1671076781706223617', 0, NULL, '2023-06-20 16:45:27', NULL, NULL, NULL, NULL, 0);
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1671076877206331394', 0, NULL, '2023-06-20 16:45:49', NULL, NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_reference
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_reference`;
CREATE TABLE `solar_user_reference` (
  `r_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户关联信息',
  `identifier` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '关联信息类型',
  `reference_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '关联信息键',
  `validated` int NOT NULL DEFAULT '0' COMMENT '是否验证 0 未验证 1 已验证',
  `after_reference_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关联后的键（主要用于第三方认证）',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `validated_time` datetime DEFAULT NULL COMMENT '验证时间',
  `login_access` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可以用于登录 0 不可以 1 可以',
  `secret_access` int NOT NULL DEFAULT '1' COMMENT '是否允许使用密码验证 0 不允许 1 允许',
  `third_part` int DEFAULT NULL COMMENT '是否为第三方认证信息 0 不是 1是',
  `u_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE,
  UNIQUE KEY `reference_key_unique` (`reference_key`) USING BTREE COMMENT '关联信息全局唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_user_reference
-- ----------------------------
BEGIN;
INSERT INTO `solar_user_reference` (`r_id`, `identifier`, `reference_key`, `validated`, `after_reference_key`, `expired_time`, `validated_time`, `login_access`, `secret_access`, `third_part`, `u_id`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', 'DEFAULT', '17790271060', 0, NULL, NULL, NULL, 1, 1, 0, '1', '2022-12-30 18:58:50', 'default', '2023-06-20 17:11:29', NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_role`;
CREATE TABLE `solar_user_role` (
  `u_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id',
  `r_id` char(19) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色id',
  UNIQUE KEY `user_role` (`u_id`,`r_id`) USING BTREE COMMENT '用户角色唯一约束'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of solar_user_role
-- ----------------------------
BEGIN;
INSERT INTO `solar_user_role` (`u_id`, `r_id`) VALUES ('1', '1668549228361736193');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
