package com.ownsolutions.merchant.main;

import com.ownsolutions.merchant.api.model.CodeGenerationRequest;
import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.impl.client.HttpClients;

import java.math.BigDecimal;

public class GenerateCode {

    private static final String apiKey = "c882be4f8fdbc5c07466a5fde4681a244409ddc0496aafa9cc6e3d38806f2999";
    private static final String privateKeyFile = "private_key_pkcs8.pem";
    //private static final String baseUrl = "https://3pi-api.phygital-pay.com";
    private static final String baseUrl = "http://localhost:9060";

    /**
     * Main method.
     *
     * @param args The input arguments
     */
    public static void main(String[] args) {

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );

        CodeGenerationRequest codeGenerationRequest = CodeGenerationRequest.builder()
            .withAmount(new BigDecimal("9.99"))
            .withCurrency("EUR")
            .withMerchantId(1)
            .withIntegratorId(2001)
            .withProductId(1)
            .withMerchantTransactionReference("sbTT112431")
            .withMerchantUserReference("cust_1127181")
            .withCodeFormat("QR")
            .withCodeSize("200x200")
            .withCodeFileType("JPG")
            .build();

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        MerchantClient merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        System.out.println("Generate code base URL: " + baseUrl);

        Response apiResponse = null;
        try {
            apiResponse = merchantClient.post(codeGenerationRequest);
        } catch (MerchantApiException rae) {
            System.out.println("Generate code failed with the following message: " + rae.getLocalizedMessage());
            System.exit(1);
        }

        System.out.println("Response Body: " + apiResponse.toString());
    }
}
