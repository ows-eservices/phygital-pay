package com.ownsolutions.merchant.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ownsolutions.merchant.api.model.CodeGenerationRequest;
import com.ownsolutions.merchant.api.model.Request;
import com.ownsolutions.merchant.exception.MerchantApiException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class RequestUtils {

    /**
     * Sign a request, optionally including additional headers in the signature.
     *
     * <ol>
     * <li>If PUT or POST, insert missing content-type</li>
     * <li>Set the request's Request-Signature header to the computed signature.</li>
     * </ol>
     *
     * @param request The request to sign
     * @param apiKey The API key to be set in header
     * @param privateKeyFile The private key to be used for digital signing
     */
    public static void setRequestHeader(HttpRequestBase request, String apiKey, String privateKeyFile)
        throws InvalidKeySpecException, SignatureException, NoSuchAlgorithmException, InvalidKeyException {

        final String method = request.getMethod().toLowerCase();
        String pkey = FileUtils.getFileContent(privateKeyFile);
        String body = "";

        if (!request.containsHeader("Shared-Key")) {
            request.addHeader("Shared-Key", apiKey);
        } else {
            request.setHeader("Shared-Key", apiKey);
        }

        if (method.equals("put") || method.equals("post")) {
            if (!request.containsHeader("Content-Type")) {
                request.addHeader("Content-Type", "application/json");
            } else {
                request.setHeader("Content-Type", "application/json");
            }
            body = getRequestBody((HttpEntityEnclosingRequestBase) request);
        }

        if (!request.containsHeader("Request-Signature") ) {
            request.addHeader("Request-Signature", SignUtils.signSHA256RSA(body, pkey));
        } else {
            request.setHeader("Request-Signature", SignUtils.signSHA256RSA(body, pkey));
        }
    }

    /**
     * Helper to safely extract a request body.  Because an {@link HttpEntity} may not be repeatable,
     * this function ensures the entity is reset after reading.  Null entities are treated as an empty string.
     *
     * @param request A request with a (possibly null) {@link HttpEntity}
     * @return The request body
     */
    public static String getRequestBody(HttpEntityEnclosingRequestBase request) {
        HttpEntity entity = request.getEntity();
        if (entity == null) {
            return "";
        }
        // May need to replace the request entity after consuming
        boolean consumed = !entity.isRepeatable();
        ByteArrayOutputStream content = new ByteArrayOutputStream();
        try {
            entity.writeTo(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy request body", e);
        }
        // Replace the now-consumed body with a copy of the content stream
        byte[] body = content.toByteArray();
        if (consumed) {
            request.setEntity(new ByteArrayEntity(body));
        }
        return content.toString();
    }

    /**
     * Helper to convert object to StringEntity.
     *
     * @param om A ObjectMapper to be used
     * @param request A code request {@link CodeGenerationRequest}
     * @return The StringEntity representation of request
     */
    public static <T> StringEntity toJsonStringEntity(ObjectMapper om, Request<T> request) throws UnsupportedEncodingException {
        try {
            return new StringEntity(om.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new MerchantApiException("Marshalling the request to Json failed", e);
        }
    }

    /**
     * Helper to convert object to JSON string.
     *
     * @param om A ObjectMapper to be used
     * @param request A code request {@link CodeGenerationRequest}
     * @return The JSON string representation of request
     */
    public static <T> String toJson(ObjectMapper om, Request<T> request) throws UnsupportedEncodingException {
        try {
            return om.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new MerchantApiException("Marshalling the request to Json failed", e);
        }
    }
}
