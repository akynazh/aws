package cn.edu.xidian.aws.pojo.vo.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkAssignVO {
    /**
     * 采摘作业编号
     */
    private long workId;
    /**
     * 员工编号
     */
    private List<Long> employeeIdList;
}
