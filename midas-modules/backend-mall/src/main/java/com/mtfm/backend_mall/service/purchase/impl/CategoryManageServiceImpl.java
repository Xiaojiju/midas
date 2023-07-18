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
package com.mtfm.backend_mall.service.purchase.impl;

import com.mtfm.backend_mall.MallCode;
import com.mtfm.backend_mall.service.purchase.CategoryManageService;
import com.mtfm.core.context.exceptions.ServiceException;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotAllowedException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.SpuManager;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.provisioning.CategoryTree;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;
/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类管理
 */
public class CategoryManageServiceImpl implements CategoryManageService, MessageSourceAware {

    private CategoryManager categoryManager;

    private SpuManager spuManager;

    private MessageSourceAccessor messages;

    public CategoryManageServiceImpl(CategoryManager categoryManager, SpuManager spuManager) {
        this.categoryManager = categoryManager;
        this.spuManager = spuManager;
    }

    @Override
    public List<CategoryTree> getTree() {
        return this.categoryManager.loadTree();
    }

    @Override
    public void createCategory(CategoryDetails categoryDetails) {
        try {
            this.categoryManager.createCategory(categoryDetails);
        } catch (PurchaseExistException exist) {
            throw new ServiceException(exist.getMessage(), MallCode.CATEGORY_NAME_EXIST.getCode());
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), MallCode.CATEGORY_PARENT_NOT_FOUND.getCode());
        } catch (PurchaseNotAllowedException notAllowed) {
            throw new ServiceException(notAllowed.getMessage(), MallCode.CATEGORY_NOT_ALLOWED_LEVEL.getCode());
        }
    }

    @Override
    public void updateCategory(CategoryDetails categoryDetails) {
        try {
            this.categoryManager.updateCategory(categoryDetails);
        } catch (PurchaseExistException exist) {
            throw new ServiceException(exist.getMessage(), MallCode.CATEGORY_NAME_EXIST.getCode());
        } catch (PurchaseNotFoundException notFound) {
            throw new ServiceException(notFound.getMessage(), MallCode.CATEGORY_PARENT_NOT_FOUND.getCode());
        } catch (PurchaseNotAllowedException notAllowed) {
            throw new ServiceException(notAllowed.getMessage(), MallCode.CATEGORY_NOT_ALLOWED_LEVEL.getCode());
        }
    }

    @Override
    public void removeCategory(long categoryId) {
        if (this.spuManager.loadCount(categoryId) > 0L) {
            throw new ServiceException(this.messages.getMessage("CategoryManageServiceImpl.removeCategory",
                    "there are still product associations under the specified category for deletion, and " +
                            "deletion is not allowed"), MallCode.CATEGORY_NOT_ALLOWED_DELETED.getCode());
        }
        try {
            this.categoryManager.removeCategoryById(categoryId);
        } catch (PurchaseExistException e) {
            throw new ServiceException(this.messages.getMessage("CategoryManageServiceImpl.removeCategoryNode",
                    "there are child node associations under the specified deleted category, and deletion " +
                            "is not allowed"), MallCode.CATEGORY_NOT_ALLOWED_DELETE_NODE.getCode());
        }
    }

    @Override
    public CategoryDetails getDetails(long categoryId) {
        return this.categoryManager.loadCategoryById(categoryId);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    protected SpuManager getSpuManager() {
        return spuManager;
    }

    public void setSpuManager(SpuManager spuManager) {
        this.spuManager = spuManager;
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
