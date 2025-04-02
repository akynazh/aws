package cn.edu.xidian.aws.pojo.vo.todo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/2
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "TodosGetVO", description = "待处理称重记录查询参数")
public class TodosGetVO {
    @Schema(description = "当前页码")
    Integer page;
    @Schema(description = "每页记录数")
    Integer size;
}
