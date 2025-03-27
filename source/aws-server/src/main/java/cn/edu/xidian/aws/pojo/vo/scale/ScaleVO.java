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
@Schema(name = "ScaleVO", description = "电子秤信息")
public class ScaleVO {
    @Schema(description = "电子秤编号")
    private Long id;
    @Schema(description = "MQTT CLIENT")
    private String sid;
    /**
     * 电子秤密钥
     */
    @Schema(description = "电子秤密钥")
    private String skey;
    /**
     * 型号
     */
    @Schema(description = "型号")
    private String model;
    /**
     * 最大量程
     */
    @Schema(description = "最大量程")
    private BigDecimal maxCapacity;
    /**
     * 最小量程
     */
    @Schema(description = "最小量程")
    private BigDecimal minCapacity;
    /**
     * 量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    @Schema(description = "量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）")
    private Integer unit;
    /**
     * 检定分度值
     */
    @Schema(description = "检定分度值")
    private Integer verificationInterval;
    /**
     * 显示分度值
     */
    @Schema(description = "显示分度值")
    private Integer displayInterval;
    /**
     * 分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    @Schema(description = "分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）")
    private Integer unitDv;
    /**
     * 通信协议，0 为 MQTT，1 为 HTTP, 2 为 CoAP, 3 为 STOMP
     */
//    @Schema(description = "通信协议，0 为 MQTT，1 为 HTTP, 2 为 CoAP, 3 为 STOMP")
//    private Integer protocol;
    /**
     * 创建时间，毫秒级时间戳
     */
    @Schema(description = "创建时间，毫秒级时间戳")
    private Long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    @Schema(description = "更新时间，毫秒级时间戳")
    private Long updateTime;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    @Schema(description = "状态，0 为禁用，1 为启用，2 为已删除")
    private Integer status;
}
