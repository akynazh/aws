package cn.edu.xidian.aws;

import cn.edu.xidian.aws.pojo.po.MqttUser;
import cn.edu.xidian.aws.repository.UserRepository;
import cn.edu.xidian.aws.service.MqttUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@SpringBootTest
@Slf4j
class AwsApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MqttUserService mqttUserService;


    @Test
    void test() throws Exception {
    }
}
