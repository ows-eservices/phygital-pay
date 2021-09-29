package com.ownsolutions.merchant;

import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthcheckTest {

    private String baseUrl;

    @Before
    public void init() {
        //baseUrl = "https://3pi-api.phygital-pay.com";
        baseUrl = "http://localhost:9060";
    }

    @Test
    public void givenValidEndPoint_thenHealthcheckIsSuccessful() throws Exception {

        String pathUri = "/v1/merchant/healthcheck";
        int status = executeGetReponseStatus(pathUri);

        assertThat(status).isNotNull();
        assertThat(status).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    public void givenInvalidEndPoint_thenHealthcheckIsNotSuccessful() throws Exception {

        String pathUri = "/v1/merchant/healthcheck2";
        int status = executeGetReponseStatus(pathUri);

        assertThat(status).isNotNull();
        assertThat(status).isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    private int executeGetReponseStatus(String path) {

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(null)
            .withPrivateKeyFile(null)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        MerchantClient merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        return merchantClient.getResponseStatus(path);
    }
}
