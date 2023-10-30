package it.almaviva.eai.ljsa.sdk.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(
        prefix = "ljsa"
)
public class LjsaConfigurationProperties  {

    private boolean sslValidation = false;

    private List<String> skipUrls;

}
