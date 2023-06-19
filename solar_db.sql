/*
 Navicat Premium Data Transfer

 Source Server         : sanshi_database
 Source Server Type    : MySQL
 Source Server Version : 50740 (5.7.40)
 Source Host           : 116.204.105.249:3306
 Source Schema         : medusa

 Target Server Type    : MySQL
 Target Server Version : 50740 (5.7.40)
 File Encoding         : 65001

 Date: 25/04/2023 17:13:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for solar_base_info
-- ----------------------------
DROP TABLE IF EXISTS `solar_base_info`;
CREATE TABLE `solar_base_info` (
  `b_id` char(19) NOT NULL COMMENT '主键',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT NULL COMMENT '性别 0 女 1 男',
  `birth` date DEFAULT NULL COMMENT '出生年月',
  `u_id` char(19) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`b_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of solar_base_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_menu
-- ----------------------------
DROP TABLE IF EXISTS `solar_menu`;
CREATE TABLE `solar_menu` (
  `m_id` char(32) NOT NULL COMMENT '主键',
  `menu_name` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `router_name` varchar(128) DEFAULT NULL COMMENT '路由名称',
  `router_link` varchar(256) DEFAULT NULL COMMENT '路由链接',
  `component_address` varchar(128) DEFAULT NULL COMMENT '组件地址',
  `menu_level` int(1) DEFAULT NULL COMMENT '级别',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `perm` varchar(128) DEFAULT NULL COMMENT '权限字符',
  `icon` varchar(128) DEFAULT NULL COMMENT '图标',
  `group_id` char(32) DEFAULT NULL COMMENT '菜单集id',
  `parent_id` char(32) DEFAULT NULL COMMENT '父级菜单',
  `hidden` int(1) DEFAULT NULL COMMENT '0 不隐藏 1 隐藏',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`m_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_organization
-- ----------------------------
DROP TABLE IF EXISTS `solar_organization`;
CREATE TABLE `solar_organization` (
  `o_id` char(32) NOT NULL COMMENT '主键',
  `organization_name` varchar(128) DEFAULT NULL COMMENT '组织名称',
  `parent_id` char(32) DEFAULT NULL COMMENT '父级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`o_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_organization
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_organization_role`;
CREATE TABLE `solar_organization_role` (
  `o_id` char(32) NOT NULL,
  `r_id` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_organization_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_position
-- ----------------------------
DROP TABLE IF EXISTS `solar_position`;
CREATE TABLE `solar_position` (
  `p_id` char(32) NOT NULL COMMENT '主键',
  `position_name` varchar(128) DEFAULT NULL COMMENT '职位名称',
  `desc` text COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_position_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_position_role`;
CREATE TABLE `solar_position_role` (
  `p_id` char(32) NOT NULL,
  `r_id` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_position_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_role`;
CREATE TABLE `solar_role` (
  `r_id` char(32) NOT NULL COMMENT '主键',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `multi_extends` int(1) DEFAULT NULL COMMENT '多继承 0 不能多继承 1 可以多继承',
  `parent_id` char(32) DEFAULT NULL COMMENT '父级',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_role_group
-- ----------------------------
DROP TABLE IF EXISTS `solar_role_group`;
CREATE TABLE `solar_role_group` (
  `g_id` char(32) NOT NULL COMMENT '主键',
  `group_name` varchar(64) DEFAULT NULL COMMENT '角色组名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`g_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户组';

-- ----------------------------
-- Records of solar_role_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_role_multi_group
-- ----------------------------
DROP TABLE IF EXISTS `solar_role_multi_group`;
CREATE TABLE `solar_role_multi_group` (
  `g_id` char(32) NOT NULL,
  `r_id` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_role_multi_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_role_router
-- ----------------------------
DROP TABLE IF EXISTS `solar_role_router`;
CREATE TABLE `solar_role_router` (
  `role_id` char(32) NOT NULL COMMENT '角色id',
  `router_id` char(32) DEFAULT NULL COMMENT '路由id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_role_router
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_router_group
-- ----------------------------
DROP TABLE IF EXISTS `solar_router_group`;
CREATE TABLE `solar_router_group` (
  `g_id` char(32) NOT NULL COMMENT '主键',
  `group_name` varchar(64) DEFAULT NULL COMMENT '菜单集名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`g_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_router_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_secret
-- ----------------------------
DROP TABLE IF EXISTS `solar_secret`;
CREATE TABLE `solar_secret` (
  `s_id` varchar(19) NOT NULL COMMENT '秘钥主键',
  `secret_key` varchar(128) DEFAULT NULL COMMENT '秘钥',
  `secret_salt` char(16) DEFAULT NULL COMMENT '盐',
  `secret_expired_time` datetime DEFAULT NULL COMMENT '秘钥过期时间',
  `u_id` char(32) DEFAULT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`s_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
  `u_id` char(19) NOT NULL COMMENT '用户全局唯一id',
  `locked` int(1) DEFAULT NULL COMMENT '是否锁定 0 没有 1 被锁定',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user
-- ----------------------------
BEGIN;
INSERT INTO `solar_user` (`u_id`, `locked`, `expired_time`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', NULL, NULL, '2022-12-30 18:57:55', 'default', NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_organization
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_organization`;
CREATE TABLE `solar_user_organization` (
  `u_id` char(32) NOT NULL,
  `o_id` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_organization
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_user_position
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_position`;
CREATE TABLE `solar_user_position` (
  `u_id` char(32) NOT NULL,
  `p_id` char(0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_position
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_user_reference
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_reference`;
CREATE TABLE `solar_user_reference` (
  `r_id` char(19) NOT NULL COMMENT '用户关联信息',
  `identifier` varchar(32) DEFAULT NULL COMMENT '关联信息类型',
  `reference_key` varchar(128) DEFAULT NULL COMMENT '关联信息键',
  `validated` int(1) DEFAULT NULL COMMENT '是否验证 0 未验证 1 已验证',
  `after_reference_key` varchar(128) DEFAULT NULL COMMENT '关联后的键（主要用于第三方认证）',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `validated_time` datetime DEFAULT NULL COMMENT '验证时间',
  `login_access` tinyint(1) DEFAULT NULL COMMENT '是否可以用于登录 0 不可以 1 可以',
  `third_part` int(1) DEFAULT NULL COMMENT '是否为第三方认证信息 0 不是 1是',
  `u_id` char(19) NOT NULL COMMENT '用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int(1) DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`r_id`) USING BTREE,
  UNIQUE KEY `reference_key_unique` (`reference_key`) USING BTREE COMMENT '关联信息全局唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_reference
-- ----------------------------
BEGIN;
INSERT INTO `solar_user_reference` (`r_id`, `identifier`, `reference_key`, `validated`, `after_reference_key`, `expired_time`, `validated_time`, `login_access`, `third_part`, `u_id`, `create_time`, `create_by`, `update_time`, `update_by`, `current_version`, `deleted`) VALUES ('1', 'default', '17790271060', 1, NULL, NULL, NULL, 1, 0, '1', '2022-12-30 18:58:50', 'default', NULL, NULL, NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for solar_user_role
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_role`;
CREATE TABLE `solar_user_role` (
  `u_id` char(19) NOT NULL,
  `r_id` char(19) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for solar_user_role_group
-- ----------------------------
DROP TABLE IF EXISTS `solar_user_role_group`;
CREATE TABLE `solar_user_role_group` (
  `u_id` char(32) DEFAULT NULL,
  `g_id` char(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of solar_user_role_group
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
