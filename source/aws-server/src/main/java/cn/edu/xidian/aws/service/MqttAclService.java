package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.pojo.po.MqttAcl;
import cn.edu.xidian.aws.repository.MqttAclRepository;
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
public class MqttAclService {
    @Autowired
    private MqttAclRepository mqttAclRepository;

    @Transactional
    public void createScalePublisher(String username) {
        createAcl(username, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_PUBLISH, Mqtt.TOPIC_SCALE);
        createAcl(username, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    @Transactional
    public void createResultSubscriber(String username) {
        createAcl(username, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_SUBSCRIBE, Mqtt.TOPIC_RESULT);
        createAcl(username, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    private void createAcl(String username, String permission, String action, String topic) {
        MqttAcl acl = new MqttAcl();
        acl.setUsername(username);
        acl.setPermission(permission);
        acl.setAction(action);
        acl.setTopic(topic);
        mqttAclRepository.save(acl);
    }
}
