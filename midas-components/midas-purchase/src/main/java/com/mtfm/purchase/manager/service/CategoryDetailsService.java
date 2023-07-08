package com.mtfm.purchase.manager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtfm.purchase.PurchaseMessageSource;
import com.mtfm.purchase.entity.Category;
import com.mtfm.purchase.manager.CategoryManager;
import com.mtfm.purchase.manager.mapper.base.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

public class CategoryDetailsService extends ServiceImpl<CategoryMapper, Category>
        implements CategoryManager, MessageSourceAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CategoryDetailsService.class);

    private MessageSourceAccessor messages = PurchaseMessageSource.getAccessor();

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
