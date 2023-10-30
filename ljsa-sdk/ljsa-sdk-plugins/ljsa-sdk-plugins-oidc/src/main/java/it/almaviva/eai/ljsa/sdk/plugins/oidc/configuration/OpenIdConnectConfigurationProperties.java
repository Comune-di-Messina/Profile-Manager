package it.almaviva.eai.ljsa.sdk.plugins.oidc.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "ljsa.oidc")
public class OpenIdConnectConfigurationProperties{

    @NotBlank
    private String issuerKey;

    private String audienceKey;

    @NotBlank
    private String urlJwkProvider;

    @NotBlank
    private String subjectKey = "sub";

    @NotBlank
    private String rolesKey = "groups";

}
