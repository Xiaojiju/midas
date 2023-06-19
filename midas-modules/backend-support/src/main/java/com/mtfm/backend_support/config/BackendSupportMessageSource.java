package com.mtfm.backend_support.config;

import com.mtfm.security.context.SolarMessageSource;
import org.springframework.context.support.MessageSourceAccessor;

public class BackendSupportMessageSource extends SolarMessageSource {

    public BackendSupportMessageSource() {
        addBasenames("i18n/backend_support_messages");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new BackendSupportMessageSource());
    }
}
