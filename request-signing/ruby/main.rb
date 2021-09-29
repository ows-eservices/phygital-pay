require 'base64'
require 'json'
require 'openssl'

# Settings
private_key_path = "../example.private.key.pem";
body = {
    :amount => 9.99,
    :currency => "EUR",
    :merchantId => 1001,
    :integratorId => 2002,
    :productId => 3001,
    :merchantTransactionReference => "sbTT112431",
    :merchantUserReference => "cust_1127181",
    :codeFormat => "QR",
    :codeSize => "200x200",
    :codeFileType => "jpeg"
};

# In a production deployment this needs to be stored securely and retrieved
# as needed, for example from AWS SecretsManager
private_key = OpenSSL::PKey::RSA.new File.read private_key_path

request_body = body.to_json
signature = private_key.sign OpenSSL::Digest::SHA256.new, request_body

puts "------------"
puts "Ruby example"
puts "------------"
puts "With the request body being:"
puts request_body
puts 
puts "And signing with the private key in " + private_key_path
puts 
puts "Set the Request-Signature header to:"
puts Base64.encode64 signature
