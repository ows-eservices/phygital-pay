package com.ownsolutions.merchant.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ownsolutions.merchant.api.model.Request;
import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import com.ownsolutions.merchant.utils.RequestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.erdtman.jcs.JsonCanonicalizer;

public class MerchantHttpClient implements MerchantClient {

    private final CloseableHttpClient httpClient;
    private final MerchantApiConfig merchantApiConfig;
    private final String baseUrl;
    private final ObjectMapper om = new ObjectMapper();

    public MerchantHttpClient(MerchantApiConfig merchantApiConfig, CloseableHttpClient httpClient) {
        if (merchantApiConfig == null) {
            throw new java.lang.NullPointerException("No merchant api configuration provided!");
        }
        this.merchantApiConfig = merchantApiConfig;
        this.baseUrl = merchantApiConfig.getBaseUrl();
        if (httpClient == null) {
            this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(merchantApiConfig.getReceiveTimeoutMs())
                    .setConnectTimeout(merchantApiConfig.getConnectTimeoutMs())
                   .build())
                .build();
        } else {
            this.httpClient = httpClient;
        }
    }

    @Override
    public <T> T post(Request<T> request) {
        HttpPost httpPost = new HttpPost(baseUrl + request.getPath());

        try {
            JsonCanonicalizer jc = new JsonCanonicalizer(RequestUtils.toJson(om, request));
            System.out.println("Request Body: " + jc.getEncodedString());
            httpPost.setEntity(new StringEntity(jc.getEncodedString()));

        } catch (UnsupportedEncodingException e) {
            throw new MerchantApiException("Unsupported encoding");
        } catch (IOException ioException) {
            throw new MerchantApiException("JCS formatting failed");
        }

        try {
            RequestUtils.setRequestHeader(httpPost, merchantApiConfig.getApiKey(), merchantApiConfig.getPrivateKeyFile());
        } catch (Exception e) {
            throw new MerchantApiException("Setting request header failed with: " + e.getLocalizedMessage());
        }

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= 300) {
                System.out.println("Response status code: " + statusLine.getStatusCode() + ", reason: "
                    + statusLine.getReasonPhrase());
            }
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity == null) {
                throw new MerchantApiException("Response contains no content");
            }
            Reader reader = new InputStreamReader(responseEntity.getContent());
            return om.readValue(reader, request.getResponseType());

        } catch (IOException ex) {
            throw new MerchantApiException("Exception during request posting: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public Response get(String path) {

        HttpRequestBase request = new HttpGet(baseUrl + path);

        try {
            RequestUtils.setRequestHeader(request, merchantApiConfig.getApiKey(), merchantApiConfig.getPrivateKeyFile());
        } catch (Exception e) {
            throw new MerchantApiException("Setting request header failed with: " + e.getLocalizedMessage());
        }

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity == null) {
                throw new MerchantApiException("Response contains no content");
            }
            Reader reader = new InputStreamReader(responseEntity.getContent());
            return om.readValue(reader, Response.class);

        } catch (IOException ex) {
            throw new MerchantApiException("Exception during GET request: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public int getResponseStatus(String path) {
        try {
            final HttpGet request = new HttpGet(baseUrl + path);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return response.getStatusLine().getStatusCode();
            }
        } catch (Exception ex) {
            throw new MerchantApiException("Exception during GET request: " + ex.getLocalizedMessage());
        }
    }

    public static MerchantHttpClient.Builder builder() {
        return new MerchantHttpClient.Builder();
    }

    public static final class Builder {
        MerchantApiConfig merchantApiConfig;
        private CloseableHttpClient httpClient;

        private Builder() {
        }

        public Builder withHttpClient(CloseableHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder withMerchantApiConfig(MerchantApiConfig merchantApiConfig) {
            this.merchantApiConfig = merchantApiConfig;
            return this;
        }

        public MerchantHttpClient build() {
            MerchantHttpClient merchantHttpClient = new MerchantHttpClient(merchantApiConfig, httpClient);
            return merchantHttpClient;
        }
    }
}
