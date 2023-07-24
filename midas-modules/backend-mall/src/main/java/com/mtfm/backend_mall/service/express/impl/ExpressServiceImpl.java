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
package com.mtfm.backend_mall.service.express.impl;

import com.mtfm.backend_mall.service.express.ExpressService;
import com.mtfm.core.ServiceCode;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.core.util.page.PageTemplate;
import com.mtfm.express.entity.Express;
import com.mtfm.express.exception.ExpressHadExistException;
import com.mtfm.express.exception.NoneExpressServiceException;
import com.mtfm.express.manager.ExpressManager;
import com.mtfm.express.manager.provisioning.ExpressItem;
import com.mtfm.express.manager.provisioning.ExpressPageQuery;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 物流服务业务
 */
public class ExpressServiceImpl implements ExpressService {

    private ExpressManager expressManager;

    public ExpressServiceImpl(ExpressManager expressManager) {
        this.expressManager = expressManager;
    }

    @Override
    public void createExpressService(Express express) {
        try {
            this.expressManager.createExpressService(express);
        } catch (ExpressHadExistException hadExist) {
            throw new ServiceException(hadExist.getMessage(), ServiceCode.DATA_EXIST.getCode());
        }
    }

    @Override
    public void updateExpressService(Express express) {
        try {
            this.expressManager.updateExpressService(express);
        } catch (NoneExpressServiceException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        } catch (ExpressHadExistException hadExist) {
            throw new ServiceException(hadExist.getMessage(), ServiceCode.DATA_EXIST.getCode());
        }
    }

    @Override
    public void removeServiceBatch(List<Long> services) {
        this.expressManager.removeServiceBatch(services);
    }

    @Override
    public Express loadByExpressId(long id) {
        try {
            return this.expressManager.loadByExpressId(id);
        } catch (NoneExpressServiceException notFound) {
            throw new ServiceException(notFound.getMessage(), ServiceCode.DATA_NOT_FOUND.getCode());
        }
    }

    @Override
    public PageTemplate<Express> loadPage(ExpressPageQuery page) {
        return this.expressManager.loadPage(page);
    }

    @Override
    public List<ExpressItem> loadExpressByName(String expressService) {
        return this.expressManager.loadExpressByName(expressService);
    }

    protected ExpressManager getExpressManager() {
        return expressManager;
    }

    public void setExpressManager(ExpressManager expressManager) {
        this.expressManager = expressManager;
    }
}
