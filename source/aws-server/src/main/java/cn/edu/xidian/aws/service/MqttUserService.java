package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.pojo.po.MqttAcl;
import cn.edu.xidian.aws.pojo.vo.user.UserRegisterVO;
import cn.edu.xidian.aws.repository.MqttAclRepository;
import cn.edu.xidian.aws.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Service
@Slf4j
public class MqttUserService {
    @Autowired
    private MqttAclRepository mqttAclRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public boolean userNotExists(String uid) {
        return !userRepository.existsByUid(uid);
    }

    private void createUser(String uid, String password, UserRole role) {
        UserRegisterVO userVO = new UserRegisterVO();
        userVO.setName(uid);
        userVO.setUid(uid);
        userVO.setRoles(role.getCode());
        userVO.setPassword(password);
        userService.addUser(userVO);
    }

    private void createAcl(String username, String permission, String action, String topic) {
        MqttAcl acl = new MqttAcl();
        acl.setUsername(username);
        acl.setPermission(permission);
        acl.setAction(action);
        acl.setTopic(topic);
        mqttAclRepository.save(acl);
    }

    @Transactional
    public void createScalePublisher(String uid, String password) {
        createUser(uid, password, UserRole.SCALE);
        createAcl(uid, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_PUBLISH, Mqtt.TOPIC_SCALE);
        createAcl(uid, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    @Transactional
    public void createScaleSubscriberAndResultPublisher(String uid, String password) {
        createUser(uid, password, UserRole.SYS);
        createAcl(uid, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_PUBLISH, Mqtt.TOPIC_RESULT);
        createAcl(uid, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_SUBSCRIBE, Mqtt.TOPIC_SCALE);
        createAcl(uid, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    @Transactional
    public void createResultSubscriber(String uid, String password) {
        createUser(uid, password, UserRole.SYS);
        createAcl(uid, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_SUBSCRIBE, Mqtt.TOPIC_RESULT);
        createAcl(uid, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }
}
