package cn.edu.xidian.aws;

import cn.edu.xidian.aws.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AwsApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void test() {
    }
}
