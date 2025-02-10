package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

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

    public void handleMessage(Message<?> message) {
        Object payload = message.getPayload();
        try {
            RecordAddVO data = new ObjectMapper().readValue(payload.toString(), RecordAddVO.class);
            log.info("Parsed data: {}", data);
            recordService.addRecord(data);
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON", e);
        }
    }
}
