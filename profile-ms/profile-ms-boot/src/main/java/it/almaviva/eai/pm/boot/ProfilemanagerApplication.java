package it.almaviva.eai.pm.boot;

import it.almaviva.eai.ljsa.sdk.core.bootstrap.EnableLjsa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication(scanBasePackages =  {"it.almaviva.eai.pm"} )
@EnableJpaRepositories("it.almaviva.eai.pm.integration")
@EntityScan("it.almaviva.eai.pm.integration")
@EnableLjsa
public class ProfilemanagerApplication {

  public static void main(final String[] args) {
    SpringApplication.run(ProfilemanagerApplication.class, args);
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
