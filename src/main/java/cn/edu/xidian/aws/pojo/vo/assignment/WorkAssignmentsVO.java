package cn.edu.xidian.aws.pojo.vo.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author akynazh@gmail.com
 * @date 1/27/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "WorkAssignmentsVO", description = "作业分配信息")
public class WorkAssignmentsVO {
    @Schema(description = "采摘作业编号")
    private Long workId;
    @Schema(description = "分配的员工列表")
    private Map<Long, String> employees;
}
