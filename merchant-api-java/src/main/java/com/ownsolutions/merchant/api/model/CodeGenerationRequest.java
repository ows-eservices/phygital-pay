package com.ownsolutions.merchant.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CodeGenerationRequest extends Request<Response>  {

    @JsonIgnore
    private final String path = "/v1/merchant/codes/";

    @JsonIgnore
    private final Class<Response> responseType = Response.class;

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
     * A unique identifier for the merchant in PP system (provided to the merchant during
     * onboarding).
     */
    private final Integer merchantId;

    /**
     * A unique identifier for the integrator (e.g., PSP) in  PP system (provided to the integrator
     * during onboarding).
     */
    private final Integer integratorId;

    /**
     * A unique identifier for the product in PP system (provided to the merchant during
     * onboarding).
     */
    private final Integer productId;

    /**
     * A reference in merchant internal system for this code generation transaction.
     */
    private final String merchantTransactionReference;

    /**
     * A unique identifier for the user in merchant system that does not change over time. The most
     * optimal merchant user reference is a completely random value, a value that uniquely
     * identifies the merchant user and is disconnected from any personal information.
     * This value should be the same for all transactions of the merchant user.
     */
    private final String merchantUserReference;

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

    @JsonCreator
    public CodeGenerationRequest(
        @JsonProperty("amount") final BigDecimal amount,
        @JsonProperty("currency") final String currency,
        @JsonProperty("merchantId") final Integer merchantId,
        @JsonProperty("integratorId") final Integer integratorId,
        @JsonProperty("productId") final Integer productId,
        @JsonProperty("merchantTransactionReference") final String merchantTransactionReference,
        @JsonProperty("merchantUserReference") final String merchantUserReference,
        @JsonProperty("codeFormat") final String codeFormat,
        @JsonProperty("codeSize") final String codeSize,
        @JsonProperty("codeFileType") final String codeFileType) {
        this.amount = amount;
        this.currency = currency;
        this.merchantId = merchantId;
        this.integratorId = integratorId;
        this.productId = productId;
        this.merchantTransactionReference = merchantTransactionReference;
        this.merchantUserReference = merchantUserReference;
        this.codeFormat = codeFormat;
        this.codeSize = codeSize;
        this.codeFileType = codeFileType;
    }

    private CodeGenerationRequest(CodeGenerationRequest.Builder builder) {
        amount = builder.amount;
        currency = builder.currency;
        merchantId = builder.merchantId;
        integratorId = builder.integratorId;
        productId = builder.productId;
        merchantTransactionReference = builder.merchantTransactionReference;
        merchantUserReference = builder.merchantUserReference;
        codeFormat = builder.codeFormat;
        codeSize = builder.codeSize;
        codeFileType = builder.codeFileType;
    }

    public static CodeGenerationRequest.Builder builder() {
        return new CodeGenerationRequest.Builder();
    }

    /*** GETTERS. ***/

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public Integer getIntegratorId() {
        return integratorId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getMerchantTransactionReference() {
        return merchantTransactionReference;
    }

    public String getMerchantUserReference() {
        return merchantUserReference;
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

    @Override
    @JsonIgnore
    public String getPath() {
        return this.path;
    }

    @Override
    @JsonIgnore
    public Class<Response> getResponseType() {
        return this.responseType;
    }

    @Override
    public String toString() {
        return "CodeGenerationRequest{" +
            "path='" + path + '\'' +
            ", responseType=" + responseType +
            ", amount=" + amount +
            ", currency='" + currency + '\'' +
            ", merchantId='" + merchantId + '\'' +
            ", integratorId='" + integratorId + '\'' +
            ", productId='" + productId + '\'' +
            ", merchantTransactionReference='" + merchantTransactionReference + '\'' +
            ", merchantUserReference='" + merchantUserReference + '\'' +
            ", codeFormat='" + codeFormat + '\'' +
            ", codeSize='" + codeSize + '\'' +
            ", codeFileType='" + codeFileType + '\'' +
            '}';
    }

    /*** BUILDER SUBCLASS. ***/

    public static final class Builder {
        private BigDecimal amount;
        private String currency;
        private Integer merchantId;
        private Integer integratorId;
        private Integer productId;
        private String merchantTransactionReference;
        private String merchantUserReference;
        private String codeFormat;
        private String codeSize;
        private String codeFileType;

        private Builder() {
        }

        public static Builder aCodeGenerationRequest() {
            return new Builder();
        }

        public Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withMerchantId(Integer merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder withIntegratorId(Integer integratorId) {
            this.integratorId = integratorId;
            return this;
        }

        public Builder withProductId(Integer productId) {
            this.productId = productId;
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

        public CodeGenerationRequest build() {
            return new CodeGenerationRequest(this);
        }
    }
}
