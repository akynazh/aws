package cn.edu.xidian.aws.pojo.vo.assignment;

import cn.edu.xidian.aws.pojo.vo.produce.ProduceVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2/5/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "AssignmentListVO", description = "工作分配信息列表")
public class AssignmentListVO {
    private List<AssignmentVO> assignmentList;
    private Long count;
}
