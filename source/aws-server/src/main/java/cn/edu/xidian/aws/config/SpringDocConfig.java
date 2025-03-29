package cn.edu.xidian.aws.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
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
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Agricultural Weighing System")
                        .version("1.0.0")
                        .description("aws-server"));
    }
}
