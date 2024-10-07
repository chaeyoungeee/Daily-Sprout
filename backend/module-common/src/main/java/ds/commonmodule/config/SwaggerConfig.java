package ds.commonmodule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(
        info = @Info(
                title = "API 명세서",
                version = "v0.0.1",
                description = "API 명세서입니다."
        )
)
@Configuration
@Slf4j
public class SwaggerConfig {

    public static final String JWT_SECURITY_SCHEME = "JWT Token";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(JWT_SECURITY_SCHEME, apiKey));
    }
//
//    @Bean
//    public GroupedOpenApi userApi() {
//        return GroupedOpenApi.builder()
//                .group("회원")
//                .pathsToMatch("/users/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi habitApi() {
//        return GroupedOpenApi.builder()
//                .group("습관")
//                .pathsToMatch("/habits/**")
//                .build();
//    }
}