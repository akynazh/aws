package cn.edu.xidian.aws;

import cn.edu.xidian.aws.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AwsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AwsApplication.class, args);
        context.getBean(UserService.class).initAdmin();
    }

}
