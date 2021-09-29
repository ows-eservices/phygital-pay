# Java Example

This example presents usage of merchant-api using Java programming language.

## Production Use

The private key used is an example key that is disclosed widely in the documentation and examples for this service. 
You should use your own private key that you generate as part of the on-boarding process.

Please store your private key securely and retrieve it as needed.

## Required Java Version

This example uses Java 8 or newer.

## Building

Execute `./mvnw clean install` to build, run tests, and create jars.

There are no dependencies that need to be installed.

## Running

Generate Code:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.GenerateCode"`

Get Code:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.GetCode"`

Healthcheck:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.Healthcheck"`

## Tests

Generate Code tests:
`mvn -Dtest="com.ownsolutions.merchant.GenerateCodeTest" test`

Get Code tests:
`mvn -Dtest="com.ownsolutions.merchant.GetCodeTest" test`

Healthcheck tests:
`mvn -Dtest="com.ownsolutions.merchant.HealthcheckTest" test`

Running all tests:
`mvn test`

