package cn.edu.xidian.aws.pojo.vo.todo;

import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/2
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "TodoListVO", description = "待处理称重记录列表")
public class TodoListVO {
    private List<TodoVO> todoVOList;
    private Long count;
}
