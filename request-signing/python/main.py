import base64
import json
from collections import OrderedDict
from Crypto.Hash import SHA256
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5

# Settings
private_key_path = "../example.private.key.pem"

# Note: The only reason we're using an OrderedDict here is so we can
#       directly match the signature from the other examples. Feel
#       free to get a JSON string of your body with the keys in any order.
#       Just make sure the string you send to the server is the string you sign.
body = OrderedDict([
  ("amount", 9.99),
  ("currency", "EUR"),
  ("merchantId", 1001),
  ("integratorId", 2002),
  ("productId", 3001),
  ("merchantTransactionReference", "sbTT112431"),
  ("merchantUserReference", "cust_1127181"),
  ("codeFormat", "QR"),
  ("codeSize", "200x200"),
  ("codeFileType", "jpeg")
])

# In a production deployment this needs to be stored securely and retrieved
# as needed, for example from AWS SecretsManager
private_key = RSA.importKey(open(private_key_path).read())

request_body = json.dumps(body, separators=(',', ':'))
hasher = SHA256.new(request_body.encode('utf-8'))
signer = PKCS1_v1_5.new(private_key)
signature = signer.sign(hasher)

print("--------------")
print("Python example")
print("--------------")
print("With the request body being:")
print(request_body)
print("\n")
print("And signing with the private key in " + private_key_path)
print("\n")
print("Set the Request-Signature header to:")
print(base64.b64encode(signature).decode())
