package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.config.MqttConfig;
import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.pojo.RecordAddVO;
import cn.edu.xidian.aws.pojo.UserAuthResultVO;
import cn.edu.xidian.aws.pojo.UserLoginVO;
import cn.edu.xidian.aws.pojo.UserPO;
import cn.edu.xidian.aws.service.UserService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class EdgeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MqttConfig.MqttGateway mqttGateway;

    @PostMapping("/user/auth")
    public ResponseEntity<UserAuthResultVO> userAuth(@RequestBody UserLoginVO vo) {
        if (userService.authUser(vo.getUsername(), vo.getPassword())) {
            UserPO user = userService.getUserByUID(vo.getUsername());
            if (UserRole.canAccessMqtt(user.getRoles())) {
                return ResponseEntity.ok(new UserAuthResultVO(Mqtt.AUTH_ALLOW));
            }
        }
        return ResponseEntity.ok(new UserAuthResultVO(Mqtt.AUTH_DENY));
    }

    @PostMapping("/weigh/record")
    public ResponseEntity<String> record(@RequestBody RecordAddVO vo) {
        if (userService.authUser(vo.getUsername(), vo.getPassword())) {
            UserPO user = userService.getUserByUID(vo.getUsername());
            if (UserRole.canAccessMqtt(user.getRoles())) {
                mqttGateway.sendToMqtt(JSON.toJSONString(vo));
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
