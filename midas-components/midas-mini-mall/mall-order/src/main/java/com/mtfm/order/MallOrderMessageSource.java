package com.mtfm.order;

import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MallOrderMessageSource extends ResourceBundleMessageSource {

    public MallOrderMessageSource() {
        this.addBasenames("i18n/mall_order_messages");
    }

    public static MessageSourceAccessor getAccessor(MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource);
    }
}
