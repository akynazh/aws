package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Mqtt;
import cn.edu.xidian.aws.pojo.po.MqttAcl;
import cn.edu.xidian.aws.pojo.po.MqttUser;
import cn.edu.xidian.aws.repository.MqttAclRepository;
import cn.edu.xidian.aws.repository.MqttUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        createUser(username, password);
        createAcl(username, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_PUBLISH, Mqtt.TOPIC_SCALE);
        createAcl(username, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    @Transactional
    public void createResultSubscriber(String username, String password) throws NoSuchAlgorithmException {
        createUser(username, password);
        createAcl(username, Mqtt.PERMISSION_ALLOW, Mqtt.ACTION_SUBSCRIBE, Mqtt.TOPIC_RESULT);
        createAcl(username, Mqtt.PERMISSION_DENY, Mqtt.ACTION_ALL, Mqtt.TOPIC_ALL);
    }

    private void createUser(String username, String password) throws NoSuchAlgorithmException {
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
    }

    private void createAcl(String username, String permission, String action, String topic) {
        MqttAcl acl = new MqttAcl();
        acl.setUsername(username);
        acl.setPermission(permission);
        acl.setAction(action);
        acl.setTopic(topic);
        mqttAclRepository.save(acl);
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
