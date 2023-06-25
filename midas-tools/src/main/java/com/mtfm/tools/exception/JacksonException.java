package com.mtfm.tools.exception;

public class JacksonException extends RuntimeException {

    public JacksonException() {
        super();
    }

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(Throwable cause) {
        super(cause);
    }

    public JacksonException(String format, Object... arguments) {
        // super(format, arguments);
    }
}
