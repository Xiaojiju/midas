# 商城模块

该模块为微信迷你商城功能集，其中主要包含商品分类、商品、物流服务、以及轮播图之类的杂项；

## 错误码

```
/**
 * 商品不存在
 */
SPU_NOT_FOUND(500300, "unable to find the specified product"),
/**
 * 分类关联有商品，不允许删除
 */
CATEGORY_NOT_ALLOWED_DELETED(500301, "there are still product associations under the specified category for deletion, and deletion is not allowed"),
/**
 * 分类包含子节点，不允许删除
 */
CATEGORY_NOT_ALLOWED_DELETE_NODE(500302, "there are child node associations under the specified deleted category, and deletion is not allowed"),
/**
 * 分类已存在
 */
CATEGORY_NAME_EXIST(500303, "category name has exist or is empty"),
/**
 * 分类的父级不存在
 */
CATEGORY_PARENT_NOT_FOUND(500304, "the parent of the added category does not exist"),
/**
 * 分类不允许添加子节点
 */
CATEGORY_NOT_ALLOWED_LEVEL(500305, "classification allows up to 3 levels, no more levels are allowed to be added"),
/**
 * 商品规格与预设的规格不匹配
 */
COMMODITY_SKU_NOT_MATCH(500306, "the product specifications do not match the preset specifications"),
/**
 * 规格商品不存在
 */
COMMODITY_NOT_FOUND(500307, "could not found commodity, maybe not exist");
```