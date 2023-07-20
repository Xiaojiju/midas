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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.express.ExpressMessageSource;
import com.mtfm.express.entity.ExpressRelation;
import com.mtfm.express.exception.NoneExpressServiceException;
import com.mtfm.express.manager.ExpressRelationManager;
import com.mtfm.express.manager.provisioning.ExpressSetting;
import com.mtfm.express.mapper.ExpressRelationMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 商品关联设置业务
 */
public class ExpressRelationManageService extends ServiceImpl<ExpressRelationMapper, ExpressRelation>
        implements ExpressRelationManager, MessageSourceAware {

    private MessageSourceAccessor messages = ExpressMessageSource.getAccessor();

    @Override
    public void setRelation(long spuId, ExpressSetting expressSetting) {
        ExpressRelation er = this.getById(spuId);
        if (expressSetting == null) {
            if (er == null) {
                throw new NoneExpressServiceException(
                        this.messages.getMessage("ExpressRelationManager.nonExpressService",
                        "No associated express service"));
            }
            return ;
        }
        if (er == null) {
            er = new ExpressRelation();
        }
        er.setExpressId(expressSetting.getExpressId());
        er.setPostageType(expressSetting.getPostageType());
        er.setPostage(expressSetting.getPostage());
        er.setSpuId(spuId);
        this.saveOrUpdate(er);
    }

    @Override
    public ExpressSetting loadSetting(long spuId) {
        return this.baseMapper.selectSetting(spuId);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
