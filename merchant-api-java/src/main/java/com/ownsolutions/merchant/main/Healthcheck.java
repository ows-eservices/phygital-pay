package com.ownsolutions.merchant.main;

import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class Healthcheck {

    //private static final String baseUrl = "https://3pi-api.phygital-pay.com";
    private static final String baseUrl = "http://localhost:9060";
    private static final String pathUri = "/v1/merchant/healthcheck";

    /**
     * Main method.
     *
     * @param args The input arguments
     */
    public static void main(String[] args) throws IOException {

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

        System.out.println("Healthcheck base URL: " + baseUrl);

        try {
            int status = merchantClient.getResponseStatus(pathUri);
            if (status != HttpStatus.SC_OK) {
                System.out.println("Healthcheck failed with response status: " + status);
                System.exit(1);
            }

            System.out.println("Healthcheck succeeded with response status: " + status);

        } catch (MerchantApiException rae) {
            System.out.println("Healthcheck failed with following message: " + rae.getLocalizedMessage());
            System.exit(1);
        }
    }
}
