package com.ownsolutions.merchant.exception;

public class MerchantApiException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public MerchantApiException(String message) {
        super(message);
    }

    public MerchantApiException(String message, Throwable cause) {
        super(message, cause);
    }
}

