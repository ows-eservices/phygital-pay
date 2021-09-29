package com.ownsolutions.merchant.enums;

import org.apache.http.HttpStatus;

public enum ReturnCodes {

    RETURN_CODE_SUCCESS(0, "SUCCESS", "The request was successful.", HttpStatus.SC_OK),
    RETURN_CODE_BAD_REQUEST(1001, "BAD_REQUEST", "The request was invalid, often due to missing a required parameter.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_INVALID_API_KEY(2001, "INVALID_API_KEY", "Authentication failed due to missing or invalid API key.", HttpStatus.SC_UNAUTHORIZED),
    RETURN_CODE_INVALID_REQ_SIG(2002, "INVALID_REQUEST_SIGNATURE", "Authentication failed due to invalid request-signature.", HttpStatus.SC_UNAUTHORIZED),
    RETURN_CODE_DUP_MERCHANT_TRANS_REF(3001, "DUPLICATE_MERCHANT_TRANSACTION_REF", "The merchant transaction reference already exists. It has to be unique.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_MERCHANT_SUSPENDED(3002, "MERCHANT_SUSPENDED", "The merchant has been suspended from processing transactions.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_INVALID_CURRENCY(3003, "INVALID_CURRENCY", "The currency is not supported on your merchant account.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_INVALID_PRODUCT(3004, "INVALID_PRODUCT", "The product does not exist or not supported on your merchant account.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_INVALID_AMOUNT(3005, "INVALID_AMOUNT", "The amount value provided is invalid, often due to low and high limits.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_INVALID_CODE_FORMAT(3006, "INVALID_CODE_FORMAT", "The specified code format is invalid. Please, check the code format, size and file type supported.", HttpStatus.SC_BAD_REQUEST),
    RETURN_CODE_MERCHANT_NOT_EXISTS(3101, "MERCHANT_NOT_EXISTS", "The merchant does not exist.", HttpStatus.SC_NOT_FOUND),
    RETURN_CODE_ORDER_ID_NOT_EXISTS(3102, "ORDER_ID_NOT_EXISTS", "Order Id does not exist.", HttpStatus.SC_NOT_FOUND),
    RETURN_CODE_GENERAL_TECHNICAL_ERROR(6001, "GENERAL_TECHNICAL_ERROR", "General technical error.", HttpStatus.SC_INTERNAL_SERVER_ERROR);

    private Integer code;
    private String description;
    private String message;  // message to be printed on voucher
    private int httpStatus;

    /**
     * Constructor
     *
     * @param code Return code
     * @param description Lexicon phrase
     * @param message Voucher message
     */
    private ReturnCodes( Integer code, String description, String message, int httpStatus ) {
        this.code = code;
        this.description = description;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Returns the return code
     *
     * @return The return code
     */
    public Integer getCode( ) {
        return code;
    }

    /**
     * Returns the description
     *
     * @return The description
     */
    public String getDescription( ) {
        return description;
    }

    /**
     * Returns the return message
     *
     * @return The message
     */
    public String getMessage( ) {
        return message;
    }

    /**
     * Returns the http status
     *
     * @return The http status
     */
    public int getHttpStatus( ) {
        return httpStatus;
    }

    /**
     * Returns the code instance
     *
     * @param code
     * @return The code instance
     */
    public static ReturnCodes getFromCode( Integer code ) {
        for ( ReturnCodes returnCode : ReturnCodes.values( ) ) {
            if ( returnCode.getCode( ).equals( code ) ) {
                return returnCode;
            }
        }
        return null;
    }
}
