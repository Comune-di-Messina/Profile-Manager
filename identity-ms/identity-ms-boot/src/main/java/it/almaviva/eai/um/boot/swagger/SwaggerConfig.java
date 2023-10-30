package it.almaviva.eai.um.boot.swagger;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

        @Bean
        public Docket api() {
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                    .title("UserManagerAPI")
                    .description("The User Manager interface allows you to manage your users")
                    .version(SwaggerConfig.class.getPackage().getImplementationVersion())
                    .build())
                .forCodeGeneration(true)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(new ApiKey("X-Auth-Token", "X-Auth-Token", "header")));

            docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("it.almaviva.eai.um.api"))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build() .protocols(Sets.newHashSet("http", "https"));

            return docket;
        }


        private SecurityContext securityContext() {
            return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
        }

        List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return Lists.newArrayList(
                new SecurityReference("X-Auth-Token", authorizationScopes));
        }

    }

