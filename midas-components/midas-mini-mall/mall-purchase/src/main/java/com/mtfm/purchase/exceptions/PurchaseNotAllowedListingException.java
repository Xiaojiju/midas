package com.mtfm.purchase.exceptions;

public class PurchaseNotAllowedListingException extends RuntimeException {

    public PurchaseNotAllowedListingException(String message) {
        super(message);
    }

    public PurchaseNotAllowedListingException(String message, Throwable cause) {
        super(message, cause);
    }
}
