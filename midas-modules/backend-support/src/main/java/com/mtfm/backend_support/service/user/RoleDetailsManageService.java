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
package com.mtfm.backend_support.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.backend_support.BackendCode;
import com.mtfm.backend_support.config.BackendSupportMessageSource;
import com.mtfm.backend_support.entity.SolarRole;
import com.mtfm.backend_support.service.RoleManager;
import com.mtfm.backend_support.service.mapper.RoleMapper;
import com.mtfm.backend_support.service.query.ValuePageQuery;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.tools.enums.Judge;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

@Transactional(rollbackFor = Exception.class)
public class RoleDetailsManageService extends ServiceImpl<RoleMapper, SolarRole> implements RoleManager, InitializingBean, MessageSourceAware {

    private MessageSourceAccessor messageSource;

    public RoleDetailsManageService() {
        this.messageSource = BackendSupportMessageSource.getAccessor();
    }

    public RoleDetailsManageService(MessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    @Override
    public boolean saveRole(SolarRole solarRole) {
        SolarRole role = existRole(solarRole.getRoleName());
        if (Objects.isNull(role)) {
            return this.save(solarRole);
        }
        throw new ServiceException(this.messageSource.getMessage("RoleService.roleDuplicate"),
                BackendCode.ROLE_NAME_DUPLICATE.getCode());
    }

    @Override
    public boolean updateRole(SolarRole solarRole) {
        SolarRole role = existRole(solarRole.getRoleName());
        if (Objects.isNull(role)) {
            return this.updateById(solarRole);
        }
        throw new ServiceException(this.messageSource.getMessage("RoleService.roleDuplicate"),
                BackendCode.ROLE_NAME_DUPLICATE.getCode());
    }

    @Override
    public boolean removeRole(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean removeRoles(Collection<String> ids) {
        return this.removeBatchByIds(ids);
    }

    @Override
    public PageTemplate<SolarRole> pageList(ValuePageQuery pageQuery) {
        QueryWrapper<SolarRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.hasText(pageQuery.getValue()), SolarRole::getRoleName, pageQuery.getValue())
                .orderBy(StringUtils.hasText(pageQuery.getOrder()), pageQuery.isAsc(), SolarRole::getCreateTime)
                .eq(SolarRole::getDeleted, Judge.NO);
        IPage<SolarRole> page = new Page<SolarRole>().setCurrent(pageQuery.getCurrent())
                .setSize(pageQuery.getSize());
        page = this.page(page, queryWrapper);
        return new PageTemplate.PageTemplateBuilder<SolarRole>().setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setItems(page.getRecords())
                .setTotal(page.getTotal())
                .build();
    }

    /**
     * 使用MessageSourceAware的时候，spring会在初始化bean的时候注入MessageSource,但是由于是多模块，spring只会加载默认的国际化文件，
     * 所以继承 {@link org.springframework.security.core.SpringSecurityMessageSource}后加载个模块的国际化失败，原因是MessageSource
     * 被覆盖了。
     * 解决办法：
     *  1. 重新覆盖{@link MessageSource}定义bean
     *  2. 不继承MessageSourceAware，使用{@link MessageSourceAccessor}进行国际化处理，具体参考{@link BackendSupportMessageSource}
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "message source could not be null");
        this.messageSource = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(RoleDetailsManageService.this.messageSource, "message source could not be null");
    }

    private SolarRole existRole(String roleName) {
        QueryWrapper<SolarRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SolarRole::getRoleName, roleName)
                .eq(SolarRole::getDeleted, Judge.NO.getCode());
        return this.baseMapper.selectOne(queryWrapper);
    }
}
