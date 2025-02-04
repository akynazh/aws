package cn.edu.xidian.aws.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author akynazh@gmail.com
 * @date 2/4/25
 * @description
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("s1", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("JWT AUTH")
                                .scheme("Bearer")))
                .info(new Info()
                        .title("Agricultural Weighing System")
                        .version("0.0.0")
                        .description("AWS"));
    }
}
