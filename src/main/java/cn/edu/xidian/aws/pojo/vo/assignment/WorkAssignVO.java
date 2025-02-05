package cn.edu.xidian.aws.pojo.vo.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "WorkAssignVO", description = "作业分配表单")
public class WorkAssignVO {
    /**
     * 采摘作业编号
     */
    @Schema(description = "采摘作业编号")
    private long workId;
    /**
     * 员工编号
     */
    @Schema(description = "员工编号列表")
    private List<Long> employeeIdList;
}
