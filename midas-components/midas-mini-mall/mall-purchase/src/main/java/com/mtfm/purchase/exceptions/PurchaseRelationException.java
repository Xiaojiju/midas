package com.mtfm.purchase.exceptions;

public class PurchaseRelationException extends RuntimeException {

    public PurchaseRelationException(String message) {
        super(message);
    }

    public PurchaseRelationException(String message, Throwable cause) {
        super(message, cause);
    }
}
