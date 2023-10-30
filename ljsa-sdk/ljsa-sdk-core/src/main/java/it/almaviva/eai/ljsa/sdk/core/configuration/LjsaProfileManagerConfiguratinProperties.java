package it.almaviva.eai.ljsa.sdk.core.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(
        prefix = "ljsa.profilemanager"
)
public class LjsaProfileManagerConfiguratinProperties {

    private String microServiceId;

    private Boolean enabled = true;

    private String contactPoint;

    @NestedConfigurationProperty
    private Security security = new Security();

    @Data
    public static class Security{
        private Boolean plaintext = true;
        private String certPath;
    }

}
