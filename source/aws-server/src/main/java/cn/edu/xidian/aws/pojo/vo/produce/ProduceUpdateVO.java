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
@Schema(name = "ProduceUpdateVO", description = "修改农产品信息表单")
public class ProduceUpdateVO {
    @Schema(description = "农产品编号")
    private Long id;
    @Schema(description = "农产品名称")
    private String name;
    @Schema(description = "农产品英文名称")
    private String nameEn;
    @Schema(description = "状态，0 为未种植，1 为在种植，2 为已删除")
    private Integer status;
}
