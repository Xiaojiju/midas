package com.mtfm.app_purchase;

import com.mtfm.security.context.SolarMessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class AppPurchaseMessageSource extends SolarMessageSource {

    public AppPurchaseMessageSource() {
        addBasenames("i18n/app_mall_messages");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new AppPurchaseMessageSource());
    }
}
