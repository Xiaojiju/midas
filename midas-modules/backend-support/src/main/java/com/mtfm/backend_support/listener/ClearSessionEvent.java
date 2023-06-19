package com.mtfm.backend_support.listener;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class ClearSessionEvent extends ApplicationEvent {

    private String userId;

    public ClearSessionEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public ClearSessionEvent(Object source, Clock clock, String userId) {
        super(source, clock);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
