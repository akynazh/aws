package cn.edu.xidian.aws.pojo.vo.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 1/27/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "MyAssignmentsVO", description = "员工被分配的作业")
public class MyAssignmentsVO {
    @Schema(description = "采摘作业编号列表")
    private List<Long> workIds;
}
