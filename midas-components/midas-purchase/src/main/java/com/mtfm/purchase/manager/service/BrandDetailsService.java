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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.Brand;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.BrandManager;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.mapper.base.BrandMapper;
import com.mtfm.purchase.manager.provisioning.BrandDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 品牌管理业务
 * 该类主要是{@link BrandManager}的实现类
 */
public class BrandDetailsService extends ServiceImpl<BrandMapper, Brand> implements BrandManager, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(BrandDetailsService.class);

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();
    private CategoryManager categoryManager;

    public BrandDetailsService(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Override
    public void createBrand(BrandDetails details) throws PurchaseExistException {

    }

    @Override
    public void updateBrand(BrandDetails details) throws PurchaseNotFoundException {

    }

    @Override
    public BrandDetails loadBrand(Long id, String brand) {
        return null;
    }

    @Override
    public List<BrandDetails> loadBrandByLetter(String letter) {
        return null;
    }

    @Override
    public void removeBrand(Long id, String brand) {

    }

    @Override
    public PageTemplate<BrandDetails> page(Page page) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
