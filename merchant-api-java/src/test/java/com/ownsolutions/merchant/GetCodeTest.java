package com.ownsolutions.merchant;

import com.ownsolutions.merchant.api.model.Response;
import com.ownsolutions.merchant.client.MerchantClient;
import com.ownsolutions.merchant.client.MerchantHttpClient;
import com.ownsolutions.merchant.config.MerchantApiConfig;
import com.ownsolutions.merchant.enums.ReturnCodes;
import java.math.BigDecimal;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCodeTest {

    private static final BigDecimal amount999 = new BigDecimal("9.99");
    private static final String currencyEUR = "EUR";
    private static final String merchantTransactionReference = "sbTT112431";
    private static final String codeFormatQR = "QR";
    private static final String codeSize200x200 = "200x200";
    private static final String codeFileTypeJPEG = "JPG";

    private String apiKey;
    private String privateKeyFile;
    private String orderId;
    private String baseUrl;
    private String pathUri;
    private MerchantClient merchantClient;

    @Before
    public void init() {
        apiKey = "c882be4f8fdbc5c07466a5fde4681a244409ddc0496aafa9cc6e3d38806f2999";
        privateKeyFile = "private_key_pkcs8.pem";
        orderId = "1";
        //baseUrl = "https://3pi-api.phygital-pay.com";
        baseUrl = "http://localhost:9060";
        pathUri = "/v1/merchant/codes/%s";

        java.security.Security.addProvider(
            new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
    }

    @Test
    public void givenValidApiKeyAndGetCodeRequest_thenGetCodeIsSuccessful() throws Exception {

        Response apiResponse = executeGetCode();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_SUCCESS.getCode());
        assertThat(apiResponse.getOrderId()).isNotNull();
        assertThat(apiResponse.getBase64Code()).isNotNull();
        assertThat(apiResponse.getAmount()).isEqualTo(amount999);
        assertThat(apiResponse.getCurrency()).isEqualTo(currencyEUR);
        assertThat(apiResponse.getMerchantTransactionReference()).isEqualToIgnoringCase(merchantTransactionReference);
        assertThat(apiResponse.getAmount()).isEqualTo(amount999);
        assertThat(apiResponse.getCodeFormat()).isEqualTo(codeFormatQR);
        assertThat(apiResponse.getCodeSize()).isEqualTo(codeSize200x200);
        assertThat(apiResponse.getCodeFileType()).isEqualTo(codeFileTypeJPEG);
    }

    @Test
    public void givenInvalidApiKey_thenGetCodeIsNotSuccessful() throws Exception {
        apiKey = "dummy";

        Response apiResponse = executeGetCode();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_INVALID_API_KEY.getCode());
        assertThat(apiResponse.getResultDescription()).isEqualToIgnoringCase(ReturnCodes.RETURN_CODE_INVALID_API_KEY.getDescription());
    }

    @Test
    public void givenInvalidOrderId_thenGetCodeIsNotSuccessful() throws Exception {
        orderId = "1111111";

        Response apiResponse = executeGetCode();

        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getResultCode()).isEqualTo(ReturnCodes.RETURN_CODE_ORDER_ID_NOT_EXISTS.getCode());
        assertThat(apiResponse.getResultDescription()).startsWith(ReturnCodes.RETURN_CODE_ORDER_ID_NOT_EXISTS.getDescription());
    }

    private Response executeGetCode() {

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

        return merchantClient.get(String.format(pathUri, orderId));
    }
}
