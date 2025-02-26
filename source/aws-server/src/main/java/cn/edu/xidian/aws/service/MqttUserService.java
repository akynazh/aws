package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.pojo.po.MqttAcl;
import cn.edu.xidian.aws.pojo.po.MqttUser;
import cn.edu.xidian.aws.repository.MqttAclRepository;
import cn.edu.xidian.aws.repository.MqttUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author akynazh@gmail.com
 * @date 2/25/25
 * @description
 */
@Service
@Slf4j
public class MqttUserService {
    @Autowired
    private MqttUserRepository mqttUserRepository;
    @Autowired
    private MqttAclRepository mqttAclRepository;
    private final static int SALT_LENGTH = 16;
    private final static SecureRandom random = new SecureRandom();

    @Transactional
    public void createScalePublisher(String username, String password) throws NoSuchAlgorithmException {
        String salt = generateSalt();
        String passwordWithSalt = password + salt;
        String hashedPassword = hashWithSHA256(passwordWithSalt);

        MqttUser user = new MqttUser();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setSalt(salt);
        user.setIsSuperuser(Mqtt.NOT_SUPERUSER);
        user.setCreated(java.time.LocalDateTime.now());
        mqttUserRepository.save(user);

        MqttAcl mqttAcl1 = new MqttAcl();
        mqttAcl1.setUsername(username);
        mqttAcl1.setPermission(Mqtt.PERMISSION_ALLOW);
        mqttAcl1.setAction(Mqtt.ACTION_PUBLISH);
        mqttAcl1.setTopic(Mqtt.TOPIC_SCALE);
        mqttAclRepository.save(mqttAcl1);

        MqttAcl mqttAcl2 = new MqttAcl();
        mqttAcl2.setUsername(username);
        mqttAcl2.setPermission(Mqtt.PERMISSION_DENY);
        mqttAcl2.setAction(Mqtt.ACTION_ALL);
        mqttAcl2.setTopic(Mqtt.TOPIC_ALL);
        mqttAclRepository.save(mqttAcl2);
    }

    public String generateSalt() {
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public String hashWithSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(Mqtt.HASH_ALGORITHM);
        byte[] hashBytes = digest.digest(input.getBytes());
        return new String(Hex.encode(hashBytes));
    }
}
