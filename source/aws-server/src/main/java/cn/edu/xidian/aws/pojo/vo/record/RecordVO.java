package cn.edu.xidian.aws.pojo.vo.record;

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
@Schema(name = "RecordVO", description = "称重记录")
public class RecordVO {
    @Schema(description = "称重记录编号")
    private Long id;

    /**
     * 采摘作业编号
     */
    @Schema(description = "采摘作业编号")
    private Long workId;

    @Schema(description = "农作物编号")
    private Long produceId;

    @Schema(description = "农作物图片地址")
    private String image;

    /**
     * 员工编号
     */
    @Schema(description = "员工编号")
    private Long employeeId;
    /**
     * 电子秤编号
     */
    @Schema(description = "电子秤编号")
    private Long scaleId;
    /**
     * 称重结果
     */
    @Schema(description = "称重结果")
    private BigDecimal dataValue;
    /**
     * 称量结果误差范围，正负多少
     */
    @Schema(description = "称量结果误差范围，正负多少")
    private BigDecimal dataErrorMargin;
    /**
     * 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    @Schema(description = "称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）")
    private Integer unit;
    /**
     * 称重时间，毫秒级时间戳
     */
    @Schema(description = "称重时间，毫秒级时间戳")
    private Long dataTime;
}
