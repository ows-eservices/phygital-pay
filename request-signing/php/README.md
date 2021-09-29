# PHP Example

This example uses only the built in `openssl_sign` function to sign requests.

## Production Use

The private key used is an example key that is disclosed widely in the documentation and examples for this service. You should use your own private key that you generate as part of the onboarding process.

Please store your private key securely using a service like AWS SecretsManager and retrieve it as needed.

## Running

This is a php console application which can be run with

```
$ php index.php
```

There are no dependencies that need to be installed.
