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
@Schema(name = "TodoDelVO", description = "完成待处理称重记录表单")
public class TodoDelVO {
    @Schema(description = "待处理称重记录编号")
    private Long id;
}
