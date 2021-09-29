const crypto = require("crypto");
const fs = require("fs");
const { promisify } = require("util");
const readFile = promisify(fs.readFile);

// Settings
const privateKeyPath = "../example.private.key.pem";
const body = {
  amount: 9.99,
  currency: "EUR",
  merchantId: 1001,
  integratorId: 2002,
  productId: 3001,
  merchantTransactionReference: "sbTT112431",
  merchantUserReference: "cust_1127181",
  codeFormat: "QR",
  codeSize: "200x200",
  codeFileType: "jpeg"
};

(async () => {
  const requestBody = JSON.stringify(body);

  // In a production deployment this needs to be stored securely and retrieved
  // as needed, for example from AWS SecretsManager
  const privateKey = await readFile(privateKeyPath, "utf8");

  const signer = crypto.createSign("RSA-SHA256");
  signer.write(requestBody);
  const base64EncodedSignature = signer.sign(privateKey).toString("base64");

  console.log("---------------");
  console.log("Node JS example");
  console.log("---------------");
  console.log("With the request body being:");
  console.log(requestBody);
  console.log();
  console.log(`And signing with the private key in ${privateKeyPath}`);
  console.log();
  console.log("Set the Request-Signature header to:");
  console.log(base64EncodedSignature);
})();
