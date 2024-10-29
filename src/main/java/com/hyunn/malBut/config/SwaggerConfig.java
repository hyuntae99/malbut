package com.hyunn.malBut.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI malButOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MalBut API")
                        .description("MalBut 서비스의 API 문서입니다.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("MalBut Service")
                                .email("hyuntae9912@gmail.com")
                                .url("https://project.hyunn.site")))
                .externalDocs(new ExternalDocumentation()
                        .description("MalBut GitHub Repository")
                        .url("https://github.com/mal-but"))
                .components(new Components()
                        .addSecuritySchemes("ApiKeyAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-KEY")
                                        .description("API 키 인증 방식")));
    }
}
