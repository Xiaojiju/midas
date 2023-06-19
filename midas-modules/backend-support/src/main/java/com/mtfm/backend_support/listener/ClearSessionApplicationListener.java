package com.mtfm.backend_support.listener;

import com.mtfm.security.core.AnySessionContext;
import com.mtfm.security.core.UserSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ClearSessionApplicationListener implements ApplicationListener<ClearSessionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ClearSessionApplicationListener.class);

    private final AnySessionContext<UserSubject> anySessionContext;

    public ClearSessionApplicationListener(AnySessionContext<UserSubject> anySessionContext) {
        this.anySessionContext = anySessionContext;
    }

    @Async
    @Override
    public void onApplicationEvent(ClearSessionEvent event) {
        String userId = event.getUserId();
        if (!StringUtils.hasText(userId)) {
            if (logger.isDebugEnabled()) {
                logger.debug("userId is null, could clear session");
            }
        }
        anySessionContext.clear(userId);
    }
}
