package com.ownsolutions.merchant.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class SignUtils {

    /**
     * Create base64 encoded signature using SHA256/RSA.
     *
     * @param body A request body to be signed
     * @param strPk A primary key
     * @return Base64 encoded signature
     */
    public static String signSHA256RSA(String body, String strPk) throws
        NoSuchAlgorithmException,
        InvalidKeySpecException,
        InvalidKeyException,
        SignatureException {

        String realPK = strPk
            .replaceAll("-----END RSA PRIVATE KEY-----", "")
            .replaceAll("-----BEGIN RSA PRIVATE KEY-----", "")
            .replaceAll("-----END PRIVATE KEY-----", "")
            .replaceAll("-----BEGIN PRIVATE KEY-----", "")
            .replaceAll("\n", "");

        byte[] b1 = Base64.getDecoder().decode(realPK.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        java.security.Signature privateSignature = java.security.Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(kf.generatePrivate(spec));
        privateSignature.update(body.getBytes(StandardCharsets.UTF_8));
        byte[] s = privateSignature.sign();
        return Base64.getEncoder().encodeToString(s);
    }
}
