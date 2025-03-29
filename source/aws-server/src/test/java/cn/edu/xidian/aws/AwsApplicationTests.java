package cn.edu.xidian.aws;

import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.pojo.vo.user.UserRegisterVO;
import cn.edu.xidian.aws.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AwsApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    void test() {
    }
}
