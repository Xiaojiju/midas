package com.mtfm.purchase.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.entity.CommodityAttribute;
import com.mtfm.purchase.manager.AttributeManager;
import com.mtfm.purchase.manager.mapper.CommodityAttributeMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
public class CommodityAttributeManageService extends ServiceImpl<CommodityAttributeMapper, CommodityAttribute>
        implements AttributeManager<CommodityAttribute> {

    @Override
    public void setAttributes(long id, List<CommodityAttribute> attributeGroupValues) {
        // 删除所有属性
        this.removeAttributes(id, null);
        for (CommodityAttribute attribute : attributeGroupValues) {
            attribute.setCommodityId(id);
        }
        this.saveBatch(attributeGroupValues);
    }

    @Override
    public void removeAttributes(long id, Collection<Long> attributes) {
        QueryWrapper<CommodityAttribute> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CommodityAttribute::getCommodityId, id)
                .in(!CollectionUtils.isEmpty(attributes), CommodityAttribute::getId, attributes);
        this.remove(queryWrapper);
    }

    @Override
    public List<CommodityAttribute> loadAttributesById(long id) {
        return this.list(new QueryWrapper<CommodityAttribute>().lambda().eq(CommodityAttribute::getCommodityId, id));
    }
}
