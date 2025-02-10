package cn.edu.xidian.aws.pojo.vo.work;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * 果实编号
     */
    private Long produceId;
    /**
     * 采摘开始时间，毫秒级时间戳
     */
    private Long startTime;
    /**
     * 采摘结束时间，毫秒级时间戳
     */
    private Long endTime;
}
