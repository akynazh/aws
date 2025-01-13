package cn.edu.xidian.aws;

import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AwsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AwsApplication.class, args);
        context.getBean(UserService.class).initAdmin("659811", "123456");
    }

}
