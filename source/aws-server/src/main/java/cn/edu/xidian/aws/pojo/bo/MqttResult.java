package cn.edu.xidian.aws.pojo.bo;

import cn.edu.xidian.aws.pojo.po.Record;
import cn.edu.xidian.aws.pojo.vo.record.RecordAddVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.messaging.Message;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/25
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MqttResult {
    private Record record;
    /**
     * 1: success, 0: fail
     */
    private Integer success;
    private String reason;
    private Message<?> message;
}
