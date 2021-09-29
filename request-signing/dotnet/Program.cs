using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;

namespace request_signer
{
    class Program
    {
        static void Main(string[] args)
        {
            // Settings
            const string privateKeyPath = "../example.private.key.pem";
            var body = new
            {
                amount = 9.99,
                currency = "EUR",
                merchantId = 1001,
                integratorId = 2002,
                productId = 3001,
                merchantTransactionReference = "sbTT112431",
                merchantUserReference = "cust_1127181",
                codeFormat = "QR",
                codeSize = "200x200",
                codeFileType = "jpeg"                
            };   

            string requestBody = JsonSerializer.Serialize(body);

            // In a production deployment this needs to be stored securely and retrieved
            // as needed, for example from AWS SecretsManager
            //
            // .NET doesn't have a built in understanding of .PEM files, but we can parse it
            // ourselves without too much trouble.
            var content = File
                .ReadAllText(privateKeyPath)
                .Replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .Replace("-----END RSA PRIVATE KEY-----", "")
                .Replace("\n", "");
            var rsa = RSA.Create();
            rsa.ImportRSAPrivateKey(Convert.FromBase64String(content), out int bytesRead);

            var signedData = rsa.SignData(
                Encoding.UTF8.GetBytes(requestBody),
                HashAlgorithmName.SHA256,
                RSASignaturePadding.Pkcs1
            );
            var base64EncodedSignature = Convert.ToBase64String(signedData);

            System.Console.WriteLine("----------");
            System.Console.WriteLine("C# example");
            System.Console.WriteLine("----------");
            System.Console.WriteLine("With the request body being:");
            System.Console.WriteLine(requestBody);
            System.Console.WriteLine();
            System.Console.WriteLine("And signing with the private key in " + privateKeyPath);
            System.Console.WriteLine();
            System.Console.WriteLine("Set the Request-Signature header to:");
            System.Console.WriteLine(base64EncodedSignature);
        }
    }
}
