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
import com.mtfm.core.util.NodeTree;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.Category;
import com.mtfm.purchase.exceptions.PurchaseExistException;
import com.mtfm.purchase.exceptions.PurchaseNotAllowedException;
import com.mtfm.purchase.exceptions.PurchaseNotFoundException;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.mapper.CategoryMapper;
import com.mtfm.purchase.manager.provisioning.CategoryDetails;
import com.mtfm.purchase.manager.provisioning.CategoryTree;
import com.mtfm.tools.StringUtils;
import com.mtfm.tools.enums.Judge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 一块小饼干
 * @since 1.0.0
 * 分类详情管理
 */
@Transactional(rollbackFor = Exception.class)
public class CategoryDetailsService extends ServiceImpl<CategoryMapper, Category>
        implements CategoryManager, MessageSourceAware, InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDetailsService.class);

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    private RedisTemplate<String, String> redisTemplate;

    private volatile NodeTree<CategoryTree> nodeTree;

    public CategoryDetailsService() {
    }

    public CategoryDetailsService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CategoryDetails loadCategoryById(Long id) {
        if (id == null) {
            throw new NullPointerException("category id could not be null");
        }
        List<CategoryDetails> categoryDetails = this.baseMapper.selectCategory(id, null);
        if (CollectionUtils.isEmpty(categoryDetails)) {
            return null;
        }
        return categoryDetails.get(0);
    }

    @Override
    public CategoryDetails loadCategoryByName(String category) {
        if (!StringUtils.hasText(category)) {
            throw new NullPointerException("category name could not be null");
        }
        List<CategoryDetails> categoryDetails = this.baseMapper.selectCategory(null, category);
        if (CollectionUtils.isEmpty(categoryDetails)) {
            return null;
        }
        return categoryDetails.get(0);
    }

    // todo 需要添加清空缓存
    @Override
    public void createCategory(CategoryDetails details) throws PurchaseExistException {
        if (details == null) {
            throw new NullPointerException("category details could not be null");
        }
        String name = details.getCategory();
        if (this.categoryExist(name)) {
            throw new PurchaseExistException(this.messages.getMessage("CategoryManager.exist",
                    "category name has exist or is empty"));
        }
        Long parentId = details.getParentId();
        if (parentId == 0L) {
            details.setLevel(0);
        } else {
            Category category = this.baseMapper.selectById(parentId);
            if (category == null) {
                throw new PurchaseNotFoundException(this.messages.getMessage("CategoryManager.notFound",
                        "the parent of the added category does not exist"));
            }
            if (category.getLevel() >= 2) {
                throw new PurchaseNotAllowedException(this.messages.getMessage("CategoryManager.notAllowedSaved",
                        "classification allows up to 3 levels, no more levels are allowed to be added"));
            }
            details.setLevel(category.getLevel() + 1);
        }
        Category category = details.convertTo();
        category.setId(null);
        this.save(category);
    }

    // todo 需要添加清空缓存
    @Override
    public void updateCategory(CategoryDetails details) throws PurchaseExistException {
        if (details == null) {
            throw new NullPointerException("category details could not be null");
        }
        String name = details.getCategory();
        Long target = details.getId();
        if (target == null) {
            throw new NullPointerException("category id is null, should be set value to it");
        }
        CategoryDetails categoryDetails = this.loadCategoryByName(name);
        if (categoryDetails != null && !categoryDetails.getId().equals(details.getId())) {
            throw new PurchaseExistException(this.messages.getMessage("CategoryManager.exist",
                    "category name has exist or is empty"));
        }
        Long parentId = details.getParentId();
        if (parentId == 0L) {
            details.setLevel(0);
        } else {
            Category category = this.baseMapper.selectById(parentId);
            if (category == null) {
                throw new PurchaseNotFoundException(this.messages.getMessage("CategoryManager.notFound",
                        "the parent of the added category does not exist"));
            }
            if (category.getLevel() >= 2) {
                throw new PurchaseNotAllowedException(this.messages.getMessage("CategoryManager.notAllowedSaved",
                        "classification allows up to 3 levels, no more levels are allowed to be added"));
            }
            details.setLevel(category.getLevel() + 1);
        }
        Category category = details.convertTo();
        this.updateById(category);
    }

    // todo 需要添加缓存
    @Override
    public List<CategoryTree> loadTree() {
        NodeTree<CategoryTree> categoryTreeNodeTree = this.buildTree();
        if (categoryTreeNodeTree == null) {
            return new ArrayList<>();
        }
        CategoryTree element = categoryTreeNodeTree.getElement();
        if (element == null) {
            return new ArrayList<>();
        }
        return element.getNodes();
    }

    @Override
    public CategoryTree loadTreeById(final Long id) {
        NodeTree<CategoryTree> categoryTreeNodeTree = this.buildTree();
        if (categoryTreeNodeTree == null) {
            return null;
        }
        NodeTree<CategoryTree> nodeTree = categoryTreeNodeTree.find((item) -> {
            if (item == null) {
                return false;
            }
            return item.getKey().equals(String.valueOf(id));
        });
        if (nodeTree == null) {
            return null;
        }
        return nodeTree.getElement();
    }

    @Override
    public CategoryTree loadTreeByName(final String category) {
        NodeTree<CategoryTree> categoryTreeNodeTree = this.buildTree();
        if (categoryTreeNodeTree == null) {
            return null;
        }
        NodeTree<CategoryTree> nodeTree = categoryTreeNodeTree.find((item) -> {
            if (item == null) {
                return false;
            }
            return item.getCategory().equals(category);
        });
        if (nodeTree == null) {
            return null;
        }
        return nodeTree.getElement();
    }

    @Override
    public void removeCategoryById(Long id) throws PurchaseExistException {
        CategoryTree categoryTree = this.loadTreeById(id);
        if (CollectionUtils.isEmpty(categoryTree.getNodes())) {
            this.removeById(id);
        }
        throw new PurchaseExistException(this.messages.getMessage("CategoryManager.existNode",
                "could not be removed category which has next node"));
    }

    @Override
    public void removeCategoryByName(String category) throws PurchaseExistException {
        CategoryTree categoryTree = this.loadTreeByName(category);
        if (CollectionUtils.isEmpty(categoryTree.getNodes())) {
            this.removeById(categoryTree.getKey());
        }
        throw new PurchaseExistException(this.messages.getMessage("CategoryManager.existNode",
                "could not be removed category which has next node"));
    }

    @Override
    public boolean categoryExist(String category) {
        if (!StringUtils.hasText(category)) {
            // 不允许分类为空且唯一
            return true;
        }
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Category::getCategory, category)
                .eq(Category::getDeleted, Judge.NO);
        return this.baseMapper.exists(queryWrapper);
    }

    // todo 需要考虑多线程抢占的问题
    private NodeTree<CategoryTree> buildTree() {
//        if (redisTemplate == null) {
//            // 没有使用redis缓存的情况
//            if (this.nodeTree != null) {
//                return this.nodeTree;
//            }
//        } else {
//            // 获取缓存
//
//        }
        List<CategoryDetails> categoryDetails = this.baseMapper.selectCategory(null, null);
        if (CollectionUtils.isEmpty(categoryDetails)) {
            return null;
        }
        List<CategoryTree> collect = categoryDetails.stream().map(item -> {
            CategoryTree categoryTree = new CategoryTree();
            categoryTree.setKey(String.valueOf(item.getId()));
            categoryTree.setParent(String.valueOf(item.getParentId()));
            categoryTree.setIcon(item.getIcon());
            categoryTree.setHeight(item.getLevel());
            categoryTree.setDisplay(item.getDisplay());
            categoryTree.setCategory(item.getCategory());
            return categoryTree;
        }).collect(Collectors.toList());
        CategoryTree root = new CategoryTree();
        root.setKey("0");
        root.setParent("0");
        root.setCategory("分类");
        NodeTree<CategoryTree> categoryTreeNodeTree = NodeTree.build(collect, root);
        this.nodeTree = categoryTreeNodeTree;
        return categoryTreeNodeTree;
    }
//
//    private void removeCache() {
//        if (redisTemplate == null) {
//            this.nodeTree = null;
//        }
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 防止初始化redisTemplate不被覆盖，如果为空，则从容器当中取
        if (this.redisTemplate == null) {
            this.redisTemplate = (RedisTemplate<String, String>) applicationContext.getBean("redisTemplate");
        }
    }
}
