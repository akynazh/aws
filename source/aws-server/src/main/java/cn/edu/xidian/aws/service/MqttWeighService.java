package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.config.MqttConfig;
import cn.edu.xidian.aws.pojo.bo.MqttResult;
import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author akynazh@gmail.com
 * @date 1/31/25
 * @description
 */
@Service
@Slf4j
public class MqttWeighService {
    @Autowired
    private RecordService recordService;
    @Autowired
    private MqttConfig.MqttGateway mqttGateway;

    public void handleMessage(Message<?> message) throws IOException {
        Object payload = message.getPayload();
        RecordAddVO data = new ObjectMapper().readValue(payload.toString(), RecordAddVO.class);
        log.info("Parsed data: {}", data);
        Record record = recordService.addRecord(data);

        MqttResult result = new MqttResult();
        result.setRecord(record);
        result.setSuccess(1);
        result.setMessage(message);
        mqttGateway.sendToMqtt(JSON.toJSONString(result));
    }
}
