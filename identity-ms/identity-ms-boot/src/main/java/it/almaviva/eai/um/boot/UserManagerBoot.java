package it.almaviva.eai.um.boot;

import it.almaviva.eai.ljsa.sdk.core.bootstrap.EnableLjsa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableLjsa
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "it.almaviva.eai.um")
public class UserManagerBoot {
    public static void main(String[] args) {
        SpringApplication.run(UserManagerBoot.class, args);
    }

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
}
