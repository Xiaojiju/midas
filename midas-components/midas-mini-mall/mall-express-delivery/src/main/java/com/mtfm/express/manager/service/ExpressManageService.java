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
package com.mtfm.express.manager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.core.util.page.Page;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.express.ExpressMessageSource;
import com.mtfm.express.entity.Express;
import com.mtfm.express.exception.ExpressHadExistException;
import com.mtfm.express.exception.NoneExpressServiceException;
import com.mtfm.express.manager.ExpressManager;
import com.mtfm.express.manager.provisioning.ExpressItem;
import com.mtfm.express.mapper.ExpressMapper;
import com.mtfm.tools.enums.Judge;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流配置
 */
@Transactional(rollbackFor = Exception.class)
public class ExpressManageService extends ServiceImpl<ExpressMapper, Express> implements ExpressManager, MessageSourceAware {

    private MessageSourceAccessor messages = ExpressMessageSource.getAccessor();

    @Override
    public void createExpressService(Express express) {
        QueryWrapper<Express> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Express::getExpressService, express.getExpressService())
                .eq(Express::getServiceType, express.getServiceType())
                .eq(Express::getDeleted, Judge.NO);
        List<Express> expresses = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(expresses)) {
            throw new ExpressHadExistException(this.messages.getMessage("ExpressRelationManager.existExpressService",
                    "a express service with the same type and name already exists"));
        }
        express.setId(null);
        this.save(express);
    }

    @Override
    public void updateExpressService(Express express) {
        QueryWrapper<Express> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Express::getExpressService, express.getExpressService())
                .eq(Express::getServiceType, express.getServiceType())
                .eq(Express::getDeleted, Judge.NO);
        List<Express> expresses = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(expresses)) {
            if (!this.updateById(express)) {
                throw new NoneExpressServiceException(this.messages.getMessage("ExpressRelationManager.nonExpressService",
                        "No associated express service"));
            }
        }
        for (Express item : expresses) {
            if (!item.getId().equals(express.getId())) {
                throw new NoneExpressServiceException(this.messages.getMessage("ExpressRelationManager.nonExpressService",
                        "No associated express service"));
            }
        }
        this.updateById(express);
    }

    @Override
    public void removeServiceBatch(List<Long> services) {
        this.removeBatchByIds(services);
    }

    @Override
    public Express loadByExpressId(long id) {
        Express express = this.getById(id);
        if (express == null) {
            throw new NoneExpressServiceException(this.messages.getMessage("ExpressRelationManager.nonExpressService",
                    "No associated express service"));
        }
        return express;
    }

    @Override
    public PageTemplate<Express> loadPage(Page page) {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Express> expressPage
                = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        expressPage.setCurrent(page.getCurrent()).setSize(page.getSize());
        expressPage = this.page(expressPage);
        return new PageTemplate<>(expressPage.getCurrent(), expressPage.getSize(), expressPage.getTotal(), expressPage.getRecords());
    }

    @Override
    public List<ExpressItem> loadExpressByName(String expressService) {
        List<ExpressItem> expressItems = this.baseMapper.selectExpressItem(expressService);
        if (CollectionUtils.isEmpty(expressItems)) {
            return new ArrayList<>();
        }
        return expressItems;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
