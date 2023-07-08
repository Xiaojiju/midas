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

 Date: 08/07/2023 20:27:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for purchase_attr
-- ----------------------------
DROP TABLE IF EXISTS `purchase_attr`;
CREATE TABLE `purchase_attr` (
  `id` bigint NOT NULL COMMENT '主键',
  `attr_name` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '属性名',
  `icon` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `attr_type` int DEFAULT NULL COMMENT '属性类型 0 销售类型 1 基本类型',
  `enable` int NOT NULL DEFAULT '1' COMMENT '是否可用 0 不可用 1 可用',
  `category_id` bigint NOT NULL COMMENT '所属分类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of purchase_attr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_attr_group
-- ----------------------------
DROP TABLE IF EXISTS `purchase_attr_group`;
CREATE TABLE `purchase_attr_group` (
  `id` bigint NOT NULL COMMENT '主键',
  `group_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分组名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  `brief` text COLLATE utf8mb4_general_ci COMMENT '简要说明',
  `icon` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组图标',
  `category_id` bigint NOT NULL COMMENT '所属分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='属性分组';

-- ----------------------------
-- Records of purchase_attr_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_attr_group_relation
-- ----------------------------
DROP TABLE IF EXISTS `purchase_attr_group_relation`;
CREATE TABLE `purchase_attr_group_relation` (
  `id` bigint NOT NULL COMMENT '主键',
  `group_id` bigint NOT NULL COMMENT '属性分组id',
  `attr_id` bigint NOT NULL COMMENT '属性id',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品属性分组关系表';

-- ----------------------------
-- Records of purchase_attr_group_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_brand
-- ----------------------------
DROP TABLE IF EXISTS `purchase_brand`;
CREATE TABLE `purchase_brand` (
  `id` bigint NOT NULL COMMENT '主键',
  `brand` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '品牌',
  `logo` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'logo',
  `description` text COLLATE utf8mb4_general_ci COMMENT '品牌描述',
  `show` int NOT NULL DEFAULT '1' COMMENT '是否展示 0 不展示 1 展示',
  `index_letter` char(1) COLLATE utf8mb4_general_ci NOT NULL COMMENT '首字母',
  `sort` int DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `version` datetime DEFAULT NULL COMMENT '版本',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品属性';

-- ----------------------------
-- Records of purchase_brand
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_brand_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `purchase_brand_category_relation`;
CREATE TABLE `purchase_brand_category_relation` (
  `id` bigint NOT NULL COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '分类id',
  `brand_id` bigint NOT NULL COMMENT '品牌id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='品牌分类关系';

-- ----------------------------
-- Records of purchase_brand_category_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_category
-- ----------------------------
DROP TABLE IF EXISTS `purchase_category`;
CREATE TABLE `purchase_category` (
  `id` bigint NOT NULL COMMENT '主键',
  `category` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级',
  `level` int NOT NULL DEFAULT '0' COMMENT '级别',
  `show` int NOT NULL DEFAULT '1' COMMENT '是否显示 0 不显示 1 显示',
  `icon` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `version` datetime DEFAULT NULL COMMENT '版本',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品属性';

-- ----------------------------
-- Records of purchase_category
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_sku
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sku`;
CREATE TABLE `purchase_sku` (
  `id` bigint NOT NULL COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT '关联产品',
  `sku_name` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `category_id` bigint NOT NULL COMMENT '关联分类',
  `brand_id` bigint NOT NULL COMMENT '关联品牌',
  `index_image` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '默认图片',
  `title` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `subtitle` varchar(128) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '副标题',
  `price` decimal(18,4) NOT NULL COMMENT '价格',
  `sale` int NOT NULL DEFAULT '0' COMMENT '销量',
  `stocks` int NOT NULL DEFAULT '0' COMMENT '库存',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `version` datetime DEFAULT NULL COMMENT '版本',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品sku';

-- ----------------------------
-- Records of purchase_sku
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_sku_attr_val
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sku_attr_val`;
CREATE TABLE `purchase_sku_attr_val` (
  `id` bigint NOT NULL COMMENT '主键',
  `sku_id` bigint NOT NULL COMMENT '关联sku',
  `attr_id` bigint NOT NULL COMMENT '关联属性',
  `attr_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名',
  `attr_val` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性值',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品属性值';

-- ----------------------------
-- Records of purchase_sku_attr_val
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_sku_image
-- ----------------------------
DROP TABLE IF EXISTS `purchase_sku_image`;
CREATE TABLE `purchase_sku_image` (
  `id` bigint NOT NULL COMMENT '主键',
  `sku_id` bigint NOT NULL COMMENT '关联sku',
  `image_url` varchar(256) COLLATE utf8mb4_general_ci NOT NULL COMMENT '图片地址',
  `sort` int DEFAULT NULL COMMENT '排序',
  `index_image` int NOT NULL DEFAULT '0' COMMENT '默认图片 0 不是 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='sku图片表';

-- ----------------------------
-- Records of purchase_sku_image
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_spu
-- ----------------------------
DROP TABLE IF EXISTS `purchase_spu`;
CREATE TABLE `purchase_spu` (
  `id` bigint NOT NULL COMMENT '主键',
  `product` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `category_id` bigint NOT NULL COMMENT '所属分类',
  `brand_id` bigint NOT NULL COMMENT '所属品牌',
  `weight` decimal(18,4) DEFAULT NULL COMMENT '重量',
  `grounding` int NOT NULL DEFAULT '0' COMMENT '上架 0 未上架 1 已上架',
  `brief` text COLLATE utf8mb4_general_ci COMMENT '简要说明',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` char(19) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `version` datetime DEFAULT NULL COMMENT '版本',
  `deleted` int NOT NULL DEFAULT '0' COMMENT '逻辑删除 0 未删除 1 已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品SPU';

-- ----------------------------
-- Records of purchase_spu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for purchase_spu_attr_val
-- ----------------------------
DROP TABLE IF EXISTS `purchase_spu_attr_val`;
CREATE TABLE `purchase_spu_attr_val` (
  `id` bigint NOT NULL COMMENT '主键',
  `spu_id` bigint NOT NULL COMMENT '关联商品',
  `attr_id` bigint NOT NULL COMMENT '关联属性',
  `attr_name` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名称',
  `attr_val` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性值',
  `sort` int DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品基本属性值';

-- ----------------------------
-- Records of purchase_spu_attr_val
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
