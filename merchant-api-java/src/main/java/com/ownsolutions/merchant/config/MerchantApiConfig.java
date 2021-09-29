package com.ownsolutions.merchant.config;

public class MerchantApiConfig {

    private String baseUrl;
    private String apiKey;
    private String privateKeyFile;
    private Integer connectTimeoutMs;
    private Integer receiveTimeoutMs;

    public MerchantApiConfig() {
    }

    public MerchantApiConfig(String baseUrl, String apiKey, String privateKeyFile, Integer connectTimeoutMs,
                             Integer receiveTimeoutMs) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.privateKeyFile = privateKeyFile;
        this.connectTimeoutMs = connectTimeoutMs;
        this.receiveTimeoutMs = receiveTimeoutMs;
    }

    private MerchantApiConfig(MerchantApiConfig.Builder builder) {
        baseUrl = builder.baseUrl;
        apiKey = builder.apiKey;
        connectTimeoutMs = builder.connectTimeoutMs;
        receiveTimeoutMs = builder.receiveTimeoutMs;
        privateKeyFile = builder.privateKeyFile;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }

    public Integer getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public Integer getReceiveTimeoutMs() {
        return receiveTimeoutMs;
    }

    public static MerchantApiConfig.Builder builder() {
        return new MerchantApiConfig.Builder();
    }

    public static final class Builder {
        private String baseUrl;
        private String apiKey;
        private String privateKeyFile;
        private Integer connectTimeoutMs;
        private Integer receiveTimeoutMs;

        private Builder() {
        }

        public static Builder aMerchantApiConfig() {
            return new Builder();
        }

        public Builder withBaseUrl(String url) {
            this.baseUrl = url;
            return this;
        }

        public Builder withApiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder withPrivateKeyFile(String privateKeyFile) {
            this.privateKeyFile = privateKeyFile;
            return this;
        }

        public Builder withConnectTimeoutMs(Integer connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
            return this;
        }

        public Builder withReceiveTimeoutMs(Integer receiveTimeoutMs) {
            this.receiveTimeoutMs = receiveTimeoutMs;
            return this;
        }

        public MerchantApiConfig build() {
            return new MerchantApiConfig(this);
        }
    }
}
