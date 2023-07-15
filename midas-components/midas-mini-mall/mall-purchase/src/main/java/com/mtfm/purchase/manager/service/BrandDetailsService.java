/*
 * Copyright 2022 一块小饼干(莫杨)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtfm.purchase.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.BrandRelationManager;
import com.mtfm.purchase.manager.mapper.BrandMapper;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌管理业务
 * 该类主要是{@link BrandManager}的实现类
 */
@Transactional(rollbackFor = Exception.class)
public class BrandDetailsService extends ServiceImpl<BrandMapper, Brand>
        implements BrandManager, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(BrandDetailsService.class);

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    private BrandRelationManager brandRelationManager;

    public BrandDetailsService(BrandRelationManager brandRelationManager) {
        this.brandRelationManager = brandRelationManager;
    }

    @Override
    public void createBrand(@NotNull BrandDetails details) throws PurchaseExistException {
        if (this.brandExist(details.getBrand())) {
            throw new PurchaseExistException(this.messages.getMessage("BrandManager.exist",
                    "brand name had exist"));
        }
        Brand brand = details.convertTo();
        brand.setId(null);
        boolean success = this.save(brand);
        if (success && logger.isDebugEnabled()) {
            logger.debug("brand {} has be saved, id: {}", brand.getBrand(), brand.getId());
        }
        List<CategoryDetails> categories = details.getCategories();
        if (!CollectionUtils.isEmpty(categories)) {
            this.brandRelationManager.setRelations(brand.getId(),
                    categories.stream().map(CategoryDetails::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public void updateBrand(@NotNull BrandDetails details) throws PurchaseNotFoundException {
        Brand exist = this.loadBrand(details.getBrand());
        if (exist != null && !exist.getId().equals(details.getId())) {
            throw new PurchaseExistException(this.messages.getMessage("BrandManager.exist",
                    "brand name had exist"));
        }
        Brand brand = details.convertTo();
        boolean success = this.save(brand);
        if (success && logger.isDebugEnabled()) {
            logger.debug("brand {} has be updated, id: {}", brand.getBrand(), brand.getId());
        }
        // 删除所有
        this.brandRelationManager.removeRelations(brand.getId(), null);
        // 然后再添加
        List<CategoryDetails> categories = details.getCategories();
        if (!CollectionUtils.isEmpty(categories)) {
            this.brandRelationManager.setRelations(brand.getId(),
                    categories.stream().map(CategoryDetails::getId).collect(Collectors.toList()));
        }
    }

    @Override
    public BrandDetails loadBrandById(long id) {
        List<BrandDetails> brandDetails = this.baseMapper.selectBrand(id, null, null);
        if (CollectionUtils.isEmpty(brandDetails)) {
            return null;
        }
        return brandDetails.get(0);
    }

    @Override
    public BrandDetails loadBrandByName(String brand) {
        if (!StringUtils.hasText(brand)) {
            throw new NullPointerException("brand name must not be null");
        }
        List<BrandDetails> brandDetails = this.baseMapper.selectBrand(null, brand, null);
        if (CollectionUtils.isEmpty(brandDetails)) {
            return null;
        }
        return brandDetails.get(0);
    }

    @Override
    public List<BrandDetails> loadBrandByLetter(String letter) {
        if (StringUtils.hasText(letter)) {
            throw new NullPointerException("letter must not be null or empty");
        }
        return this.baseMapper.selectBrand(null, null, letter);
    }

    @Override
    public void removeBrandById(Long id) {
        if (id == null) {
            throw new NullPointerException("brand id must not be null");
        }
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Brand::getId, id).eq(Brand::getDeleted, Judge.NO);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    public void removeBrandByName(String brand) {
        if (!StringUtils.hasText(brand)) {
            throw new NullPointerException("brand name must not be null");
        }
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StringUtils.hasText(brand), Brand::getBrand, brand).eq(Brand::getDeleted, Judge.NO);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    public boolean brandExist(String brand) {

        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Brand::getBrand, brand)
                .eq(Brand::getDeleted, Judge.NO.getCode());
        return this.baseMapper.exists(queryWrapper);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    private Brand loadBrand(String brand) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Brand::getBrand, brand)
                .eq(Brand::getDeleted, Judge.NO.getCode());
        return this.baseMapper.selectOne(queryWrapper);
    }

    protected BrandRelationManager getBrandRelationManager() {
        return brandRelationManager;
    }

    public void setBrandRelationManager(BrandRelationManager brandRelationManager) {
        this.brandRelationManager = brandRelationManager;
    }
}
