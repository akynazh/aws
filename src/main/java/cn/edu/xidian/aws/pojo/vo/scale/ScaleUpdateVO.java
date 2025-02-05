package cn.edu.xidian.aws.pojo.vo.scale;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "ScaleUpdateVO", description = "更新电子秤信息表单")
public class ScaleUpdateVO {
    @Schema(description = "电子秤编号")
    private long id;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    @Schema(description = "状态，0 为禁用，1 为启用，2 为已删除")
    private int status;
}
