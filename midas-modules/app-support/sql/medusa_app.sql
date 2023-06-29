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

 Date: 29/06/2023 10:55:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_account
-- ----------------------------
DROP TABLE IF EXISTS `app_account`;
CREATE TABLE `app_account` (
  `id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `locked` int NOT NULL DEFAULT '0' COMMENT '0 未锁定 1 已锁定\n',
  `expired_time` datetime DEFAULT NULL COMMENT '账号过期时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '更新人',
  `current_version` datetime DEFAULT NULL COMMENT '乐观锁',
  `deleted` int DEFAULT NULL COMMENT '逻辑删除 0 未删除 1 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of app_account
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for app_user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `app_user_base_info`;
CREATE TABLE `app_user_base_info` (
  `id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `nickname` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `gender` int DEFAULT NULL COMMENT '0 男 1 女',
  `birth` date DEFAULT NULL COMMENT '生日',
  `country` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '国家',
  `province` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',
  `user_id` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of app_user_base_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for app_user_reference
-- ----------------------------
DROP TABLE IF EXISTS `app_user_reference`;
CREATE TABLE `app_user_reference` (
  `id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `username` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `identifier` varchar(128) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '认证方式',
  `additional_key` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '附带信息',
  `validated` int NOT NULL DEFAULT '1' COMMENT '是否认证 0 未认证 1 已认证',
  `validated_time` datetime DEFAULT NULL COMMENT '验证时间',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `login_access` int NOT NULL DEFAULT '1' COMMENT '可以用于登陆 0 不能 1 能',
  `secret_access` int NOT NULL DEFAULT '1' COMMENT '可以使用密钥认证 0 不可能 1 可以',
  `third_part` int NOT NULL DEFAULT '0' COMMENT '是否为第三方 0不是 1是',
  `user_id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of app_user_reference
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for app_user_secret
-- ----------------------------
DROP TABLE IF EXISTS `app_user_secret`;
CREATE TABLE `app_user_secret` (
  `id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `secret` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密钥',
  `expired_time` datetime DEFAULT NULL COMMENT '过期时间',
  `user_id` char(19) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of app_user_secret
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
