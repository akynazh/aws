package cn.edu.xidian.aws;

import cn.edu.xidian.aws.service.MqttUserService;
import cn.edu.xidian.aws.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class AwsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AwsApplication.class, args);
        context.getBean(UserService.class).initAdmin();
    }

}
