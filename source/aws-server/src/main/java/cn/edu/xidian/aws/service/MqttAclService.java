package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.repository.MqttAclRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author akynazh@gmail.com
 * @date 2/26/25
 * @description
 */
@Service
@Slf4j
public class MqttAclService {
    @Autowired
    private MqttAclRepository mqttAclRepository;
}
