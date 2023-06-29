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

 Date: 28/06/2023 16:43:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_organization_role`;
CREATE TABLE `solar_organization_role` (
  `o_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `r_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_organization_role
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
  `multi_extends` int DEFAULT NULL COMMENT '多继承 0 不能多继承 1 可以多继承',
  `parent_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_role
-- ----------------------------
BEGIN;
COMMIT;


-- ----------------------------
-- Table structure for solar_secret
-- ----------------------------
DROP TABLE IF EXISTS `solar_secret`;
CREATE TABLE `solar_secret` (
  `s_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '秘钥主键',
  `secret_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '秘钥',
  `secret_salt` char(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '盐',
  `secret_expired_time` datetime DEFAULT NULL COMMENT '秘钥过期时间',
  `u_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_secret
-- ----------------------------
BEGIN;
INSERT INTO `solar_secret` (`s_id`, `secret_key`, `secret_salt`, `secret_expired_time`, `u_id`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', '{bcrypt}$2a$10$.7lywlJXSujuL/KnPo9Nh.BvzcTcioh8RDzksYMT5Ju87SWqpLGXK', '123456', '2023-01-29 18:59:13', '1', '2022-12-30 18:59:21', 'default', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for solar_user
-- ----------------------------
DROP TABLE IF EXISTS `solar_user`;
CREATE TABLE `solar_user` (
  `u_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户全局唯一id',
  `nickname` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '用户名称',
  `avatar` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像地址',
  `gender` int DEFAULT NULL COMMENT '性别 0 男 1 女',
  `alive` int DEFAULT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user
-- ----------------------------
BEGIN;
INSERT INTO `solar_user` (`u_id`, `nickname`, `avatar`, `gender`, `alive`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', 'moyang', NULL, 1, 1, '2022-12-30 18:57:55', 'default', NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_reference
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_reference`;
CREATE TABLE `solar_user_reference` (
  `r_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户关联信息',
  `identifier` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关联信息类型',
  `reference_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关联信息键',
  `validated` int DEFAULT NULL COMMENT '是否验证 0 未验证 1 已验证',
  `after_reference_key` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '关联后的键（主要用于第三方认证）',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `validated_time` datetime DEFAULT NULL COMMENT '验证时间',
  `login_allowed` tinyint(1) DEFAULT NULL COMMENT '是否可以用于登录 0 不可以 1 可以',
  `third_part` int DEFAULT NULL COMMENT '是否为第三方认证信息 0 不是 1是',
  `alive` int DEFAULT NULL COMMENT '状态',
  `u_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE,
  UNIQUE KEY `reference_key_unique` (`reference_key`) USING BTREE COMMENT '关联信息全局唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_reference
-- ----------------------------
BEGIN;
INSERT INTO `solar_user_reference` (`r_id`, `identifier`, `reference_key`, `validated`, `after_reference_key`, `expired_time`, `validated_time`, `login_allowed`, `third_part`, `alive`, `u_id`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', 'default', '17790271060', 1, NULL, NULL, NULL, 1, 0, 1, '1', '2022-12-30 18:58:50', 'default', NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_role`;
CREATE TABLE `solar_user_role` (
  `u_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `r_id` char(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_role
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
