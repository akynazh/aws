package cn.edu.xidian.aws;

import cn.edu.xidian.aws.pojo.po.User;
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
        User user = new User();
        user.setUid("uid123");
        user.setName("name123");
        user.setPassword("password123");
        user.setRoles("ROLE_EMPLOYEE,ROLE_ADMIN");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(1);

        userRepository.save(user);
    }
}
