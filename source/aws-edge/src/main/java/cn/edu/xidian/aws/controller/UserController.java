package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.EmqxAuthStatus;
import cn.edu.xidian.aws.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description 用户服务模块
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/mqtt/auth")
    public ResponseEntity<User.EmqxAuthResultVO> mqttAuth(@RequestBody cn.edu.xidian.aws.pojo.vo.user.UserLoginVO vo) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(vo.getUid(), vo.getPassword())
        );
        if (auth.isAuthenticated()) {
            return ResponseEntity.ok(new User.EmqxAuthResultVO(EmqxAuthStatus.ALLOW, false));
        } else {
            return ResponseEntity.ok(new User.EmqxAuthResultVO(EmqxAuthStatus.DENY, false));
        }
    }
}
