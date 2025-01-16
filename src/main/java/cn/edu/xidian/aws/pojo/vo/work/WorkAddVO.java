package cn.edu.xidian.aws.pojo.vo.work;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkAddVO {

    /**
     * 农作物编号
     */
    private long produceId;
    /**
     * 采摘开始时间，毫秒级时间戳
     */
    private long startTime;
    /**
     * 采摘结束时间，毫秒级时间戳
     */
    private long endTime;
}
