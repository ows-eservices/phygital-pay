package com.ownsolutions.merchant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Response {

    /**
     * Unique identifier for a code generation order in PP system.
     */
    private final Integer orderId;

    /**
     * Internet date and time format value specifying when this code generation order was created.
     */
    private final String created;

    /**
     * Internet date and time format value specifying when this order was last time updated.
     */
    private final String updated;

    /**
     * The current status of the order (READY, FAILED, USED, EXPIRED, INVALIDATED, REVOKED).
     */
    private final String status;

    /**
     * Amount to be bound to this code, precision must be 2 digits after the decimal point
     * (e.g., 9.99).
     */
    private final BigDecimal amount;

    /**
     * The three-letter ISO 4217 designator for the code amount currency.
     */
    private final String currency;

    /**
     * A reference in merchant internal system for this code generation transaction.
     */
    private final String merchantTransactionReference;

    /**
     * A reference in merchant internal system for the user.
     */
    private final String merchantUserReference;

    /**
     * The base64 encoded code image in format, type and size specified.
     */
    private final String base64Code;

    /**
     * Code format requested (TEXT | QR |  EAN-13 | EAN-8 | CODE-128)
     */
    private final String codeFormat;

    /**
     * Parameter used in case QR code is set as a code format. Specifies the size of the QR code
     * image you want to generate (in px for raster graphic formats like png, gif or jpeg);
     * as logical unit for vector graphics (svg, eps). Default value: 200x200
     */
    private final String codeSize;

    /**
     * The type of output image file. There are different formats available: png, jpeg, gif, svg,
     * eps.
     */
    private final String codeFileType;

    /**
     * Numerical result code of code generation order. “0” is a valid order otherwise an error
     * code is set.
     */
    private final Integer resultCode;

    /**
     * Text message of the code generation order, depending on the result code.
     */
    private final String resultDescription;

    @JsonCreator
    public Response(
        @JsonProperty("orderId") final Integer orderId,
        @JsonProperty("created") final String created,
        @JsonProperty("updated") final String updated,
        @JsonProperty("amount") final BigDecimal amount,
        @JsonProperty("status") final String status,
        @JsonProperty("currency") final String currency,
        @JsonProperty("merchantTransactionReference") final String merchantTransactionReference,
        @JsonProperty("merchantUserReference") final String merchantUserReference,
        @JsonProperty("base64Code") final String base64Code,
        @JsonProperty("codeFormat") final String codeFormat,
        @JsonProperty("codeSize") final String codeSize,
        @JsonProperty("codeFileType") final String codeFileType,
        @JsonProperty("resultCode") final Integer resultCode,
        @JsonProperty("resultDescription") final String resultDescription) {
        this.orderId = orderId;
        this.created = created;
        this.updated = updated;
        this.amount = amount;
        this.status = status;
        this.currency = currency;
        this.merchantTransactionReference = merchantTransactionReference;
        this.merchantUserReference = merchantUserReference;
        this.base64Code = base64Code;
        this.codeFormat = codeFormat;
        this.codeSize = codeSize;
        this.codeFileType = codeFileType;
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
    }

    private Response(Builder builder) {
        orderId = builder.orderId;
        created = builder.created;
        updated = builder.updated;
        amount = builder.amount;
        status = builder.status;
        currency = builder.currency;
        merchantTransactionReference = builder.merchantTransactionReference;
        merchantUserReference = builder.merchantUserReference;
        base64Code = builder.base64Code;
        codeFormat = builder.codeFormat;
        codeSize = builder.codeSize;
        codeFileType = builder.codeFileType;
        resultCode = builder.resultCode;
        resultDescription = builder.resultDescription;
    }

    public static Builder builder() {
        return new Builder();
    }

    /*** GETTERS. ***/

    public Integer getOrderId() {
        return orderId;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMerchantTransactionReference() {
        return merchantTransactionReference;
    }

    public String getMerchantUserReference() {
        return merchantUserReference;
    }

    public String getBase64Code() {
        return base64Code;
    }

    public String getCodeFormat() {
        return codeFormat;
    }

    public String getCodeSize() {
        return codeSize;
    }

    public String getCodeFileType() {
        return codeFileType;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    @Override
    public String toString() {
        return "Response{" +
            "orderId=" + orderId +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", status='" + status + '\'' +
            ", amount=" + amount +
            ", currency='" + currency + '\'' +
            ", merchantTransactionReference='" + merchantTransactionReference + '\'' +
            ", merchantUserReference='" + merchantUserReference + '\'' +
            ", base64Code='" + base64Code + '\'' +
            ", codeFormat='" + codeFormat + '\'' +
            ", codeSize='" + codeSize + '\'' +
            ", codeFileType='" + codeFileType + '\'' +
            ", resultCode=" + resultCode +
            ", resultDescription='" + resultDescription + '\'' +
            '}';
    }

    /*** BUILDER SUBCLASS. ***/

    public static final class Builder {

        private Integer orderId;
        private String created;
        private String updated;
        private String status;
        private BigDecimal amount;
        private String currency;
        private String merchantTransactionReference;
        private String merchantUserReference;
        private String base64Code;
        private String codeFormat;
        private String codeSize;
        private String codeFileType;
        private Integer resultCode;
        private String resultDescription;

        private Builder() {
        }

        public static Builder aResponse() {
            return new Builder();
        }

        public Builder withOrderId(Integer orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder withCreated(String created) {
            this.created = created;
            return this;
        }

        public Builder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withMerchantTransactionReference(String merchantTransactionReference) {
            this.merchantTransactionReference = merchantTransactionReference;
            return this;
        }

        public Builder withMerchantUserReference(String merchantUserReference) {
            this.merchantUserReference = merchantUserReference;
            return this;
        }

        public Builder withBase64Code(String base64Code) {
            this.base64Code = base64Code;
            return this;
        }

        public Builder withCodeFormat(String codeFormat) {
            this.codeFormat = codeFormat;
            return this;
        }

        public Builder withCodeSize(String codeSize) {
            this.codeSize = codeSize;
            return this;
        }

        public Builder withCodeFileType(String codeFileType) {
            this.codeFileType = codeFileType;
            return this;
        }

        public Builder withResultCode(Integer resultCode) {
            this.resultCode = resultCode;
            return this;
        }

        public Builder withResultDescription(String resultDescription) {
            this.resultDescription = resultDescription;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
