package it.almaviva.eai.um.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "wso2")
public class UserManagerProperty {

   @NotNull
   @NotEmpty
   String username;

   @NotNull
   @NotEmpty
   String password;

   @NotNull
   @NotEmpty
   String url;


}
