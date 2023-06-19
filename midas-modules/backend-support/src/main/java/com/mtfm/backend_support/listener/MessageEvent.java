package com.mtfm.backend_support.listener;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent {

    private String type;
    private String message;
    private String to;

    public MessageEvent(Object source, String type, String message, String to) {
        super(source);
        this.type = type;
        this.message = message;
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
