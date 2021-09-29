package com.ownsolutions.merchant;

import com.ownsolutions.merchant.api.model.CodeGenerationRequest;
import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.enums.ReturnCodes;
import com.ownsolutions.merchant.enums.Status;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateCodeTest {

    private static final BigDecimal amount100 = new BigDecimal(100);
    private static final Integer merchantId = 1;
    private static final Integer integratorId = 1;
    private static final Integer productId = 1;
    private static final String currencyEUR = "EUR";
    private static final String merchantTransactionReference = "sbTT112431";
    private static final String merchantUserReference = "cust_1127181";
    private static final String codeFormatQR = "QR";
    private static final String codeSize200x200 = "200x200";
    private static final String codeFileTypeJPEG = "JPEG";

    private String apiKey;
    private String privateKeyFile;
    private String baseUrl;
    private MerchantClient merchantClient;

    @Before
    public void init() {
        apiKey = "c882be4f8fdbc5c07466a5fde4681a244409ddc0496aafa9cc6e3d38806f2999";
        privateKeyFile = "private_key_pkcs8.pem";
        //baseUrl = "https://3pi-api.phygital-pay.com";
        baseUrl = "http://localhost:9060";

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();
    }

    @Test
    public void givenValidApiKeyAnCodeGenerationRequest_thenCodeGenerationIsSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withAmount(amount100)
                .withCurrency(currencyEUR)
                .withMerchantId(merchantId)
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat(codeFormatQR)
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        Response apiResponse = executeGenerateCode(codeGenerationRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_SUCCESS.getCode());
        assertThat(apiResponse.getOrderId()).isNotNull();
        assertThat(apiResponse.getBase64Code()).isNotNull();
        assertThat(apiResponse.getAmount()).isEqualTo(amount100);
        assertThat(apiResponse.getCurrency()).isEqualTo(currencyEUR);
        assertThat(apiResponse.getMerchantTransactionReference()).isEqualToIgnoringCase(merchantTransactionReference);
        assertThat(apiResponse.getAmount()).isEqualTo(amount100);
        assertThat(apiResponse.getStatus()).isEqualTo(Status.READY.name());
        assertThat(apiResponse.getCodeSize()).isEqualTo(codeSize200x200);
        assertThat(apiResponse.getCodeFileType()).isEqualTo(codeFileTypeJPEG);
    }

    @Test
    public void givenInvalidApiKey_thenCodeGenerationIsNotSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withAmount(amount100)
                .withCurrency(currencyEUR)
                .withMerchantId(merchantId)
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat(codeFormatQR)
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        apiKey = "dummy";

        Response apiResponse = executeGenerateCode(codeGenerationRequest);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_INVALID_API_KEY.getCode());
        assertThat(apiResponse.getResultDescription()).isEqualToIgnoringCase(ReturnCodes.RETURN_CODE_INVALID_API_KEY.getDescription());
    }

    @Test
    public void givenInvalidMerchantId_thenCodeGenerationIsNotSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withAmount(amount100)
                .withCurrency(currencyEUR)
                .withMerchantId(4001) // Not existing merchant id
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat(codeFormatQR)
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        Response apiResponse = executeGenerateCode(codeGenerationRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_MERCHANT_NOT_EXISTS.getCode());
        assertThat(apiResponse.getResultDescription()).isEqualToIgnoringCase(ReturnCodes.RETURN_CODE_MERCHANT_NOT_EXISTS.getDescription());
    }

    @Test
    public void givenInvalidCodeFormat_thenCodeGenerationIsNotSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withAmount(amount100)
                .withCurrency(currencyEUR)
                .withMerchantId(merchantId)
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat("docx") // invalid code format
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        Response apiResponse = executeGenerateCode(codeGenerationRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_BAD_REQUEST.getCode());
        assertThat(apiResponse.getResultDescription()).startsWith(ReturnCodes.RETURN_CODE_BAD_REQUEST.getDescription());
    }

    @Test
    public void givenInvalidCurrency_thenCodeGenerationIsNotSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withAmount(amount100)
                .withCurrency("HRK") // invalid code format
                .withMerchantId(merchantId)
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat(codeFormatQR)
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        Response apiResponse = executeGenerateCode(codeGenerationRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_INVALID_CURRENCY.getCode());
        assertThat(apiResponse.getResultDescription()).isEqualToIgnoringCase(ReturnCodes.RETURN_CODE_INVALID_CURRENCY.getDescription());
    }

    @Test
    public void givenNoAmount_thenCodeGenerationIsNotSuccessful() throws Exception {
        CodeGenerationRequest codeGenerationRequest =
            CodeGenerationRequest.builder()
                .withCurrency(currencyEUR)
                .withMerchantId(merchantId)
                .withIntegratorId(integratorId)
                .withProductId(productId)
                .withMerchantTransactionReference(merchantTransactionReference)
                .withMerchantUserReference(merchantUserReference)
                .withCodeFormat("docx") // invalid code format
                .withCodeSize(codeSize200x200)
                .withCodeFileType(codeFileTypeJPEG)
                .build();

        Response apiResponse = executeGenerateCode(codeGenerationRequest, this.merchantClient);

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_BAD_REQUEST.getCode());
        assertThat(apiResponse.getResultDescription()).startsWith(ReturnCodes.RETURN_CODE_BAD_REQUEST.getDescription());
    }

    private Response executeGenerateCode(CodeGenerationRequest codeGenerationRequest) {

        MerchantApiConfig config = MerchantApiConfig.builder()
            .withBaseUrl(baseUrl)
            .withApiKey(apiKey)
            .withPrivateKeyFile(privateKeyFile)
            .withConnectTimeoutMs(1000)
            .withReceiveTimeoutMs(20000)
            .build();

        merchantClient = MerchantHttpClient.builder()
            .withMerchantApiConfig(config)
            .withHttpClient(HttpClients.createDefault())
            .build();

        return merchantClient.post(codeGenerationRequest);
    }

    private Response executeGenerateCode(CodeGenerationRequest codeGenerationRequest, MerchantClient merchantClient) {
        return merchantClient.post(codeGenerationRequest);
    }
}