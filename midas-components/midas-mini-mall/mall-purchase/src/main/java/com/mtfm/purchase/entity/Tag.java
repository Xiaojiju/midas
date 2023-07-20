package com.mtfm.purchase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品标签
 */
@TableName(value = "purchase_tag", autoResultMap = true)
public class Tag implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("tag_name")
    private String tagName;

    @TableField("spu_id")
    private Long spuId;

    public Tag() {
    }

    public Tag(Long id, String tagName, Long spuId) {
        this.id = id;
        this.tagName = tagName;
        this.spuId = spuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
}
