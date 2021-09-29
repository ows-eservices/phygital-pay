#!/usr/local/bin/php
<?php

// Settings
$private_key_path = "../example.private.key.pem";
$body = [
    "amount" => 9.99,
    "currency" => "EUR",
    "merchantId" => 1001,
    "integratorId" => 2002,
    "productId" => 3001,
    "merchantTransactionReference" => "sbTT112431",
    "merchantUserReference" => "cust_1127181",
    "codeFormat" => "QR",
    "codeSize" => "200x200",
    "codeFileType" => "jpeg"
];

// In a production deployment this needs to be stored securely and retrieved
// as needed, for example from AWS SecretsManager
$private_key = file_get_contents($private_key_path);

$request_body = json_encode($body);
$binary_signature = "";
openssl_sign($request_body, $binary_signature, $private_key, "SHA256");

print("-----------\n");
print("PHP example\n");
print("-----------\n");
print("With the request body being:\n");
print($request_body . "\n");
print("\n");
print("And signing with the private key in " . $private_key_path . "\n");
print("\n");
print("Set the Request-Signature header to:\n");
print(base64_encode($binary_signature) . "\n");
?>