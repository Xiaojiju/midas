package com.mtfm.backend_support.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements ApplicationListener<MessageEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void onApplicationEvent(MessageEvent event) {
        logger.info("create user and send message");
    }
}
