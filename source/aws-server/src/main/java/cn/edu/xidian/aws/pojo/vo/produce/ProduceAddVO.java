package cn.edu.xidian.aws.pojo.vo.produce;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "ProduceAddVO", description = "新增农产品表单")
public class ProduceAddVO {
    @Schema(description = "农产品名称")
    private String name;
    @Schema(description = "农产品英文名称")
    private String nameEn;
}
