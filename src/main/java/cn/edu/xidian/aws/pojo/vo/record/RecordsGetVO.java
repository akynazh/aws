package cn.edu.xidian.aws.pojo.vo.record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 1/28/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecordsGetVO {
    int page;
    int size;
    /**
     * 采摘作业编号
     */
    private long workId;
    /**
     * 员工编号
     */
    private long employeeId;
    /**
     * 电子秤编号
     */
    private long scaleId;
}
