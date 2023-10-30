# OpenID Connect plugin
## Maven Dependencies
First, we will need the following dependencies in our pom.xml:


>       <dependency>
>           <groupId>it.almaviva.eai.ljsa</groupId>
>           <artifactId>ljsa-sdk-plugins-oidc</artifactId>
>           <version>VERSION</version>
>        </dependency>

## Security Configuration

~~~
ljsa:
  authentication-framework: oidc
  oidc:
    audience-key: _enEsn25UGptPkuuXL3TmnGKJ_Ia
    issuer-key: https://localhost:9443/oauth2/token
    url-jwk-provider: https://localhost:9443/oauth2/jwks
    subject-key: sub
    roles-key: groups
    token-header: x-jwt-assertion
~~~
