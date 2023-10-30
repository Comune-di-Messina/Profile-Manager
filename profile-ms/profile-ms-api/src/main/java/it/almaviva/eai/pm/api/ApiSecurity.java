package it.almaviva.eai.pm.api;

import it.almaviva.eai.pm.api.ljsa.LocalProfileManagerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ApiSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private LocalProfileManagerFilter ljsaProfileManagerFilter;

    @Override
    protected void configure( HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
                .hasRole("ACTUATOR_ADMIN")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()
                .antMatchers("/admin/*").hasRole("PM_ADMIN")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(ljsaProfileManagerFilter, BasicAuthenticationFilter.class);

        httpSecurity.cors();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/v2/api-docs","/actuator/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**");
    }

    @Bean
    public FilterRegistrationBean<LocalProfileManagerFilter> registerRequestSecurityFilter() {
        FilterRegistrationBean<LocalProfileManagerFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(ljsaProfileManagerFilter);
        bean.setEnabled(false);

        return bean;
    }
}
