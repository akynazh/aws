package cn.edu.xidian.aws.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "RecordAddVO", description = "新增称重记录表单")
public class RecordAddVO {
    @Schema(description = "uid")
    @JSONField(serialize = false)
    private String username;
    @Schema(description = "用户密码")
    @JSONField(serialize = false)
    private String password;

    /**
     * 果实 Id
     */
    @Schema(description = "果实 Id")
    private Long produceId;
    /**
     * 果实名称
     */
    @Schema(description = "果实名称")
    private String produceName;
    /**
     * 采摘作业图片 URL
     */
    @Schema(description = "采摘作业图片 URL")
    private String image;
    /**
     * 采摘作业图片 base64 编码
     */
    @Schema(description = "采摘作业图片 base64 编码")
    private String image64;
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
    @Schema(description = "称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉")
    private Integer unit;
    /**
     * 称重时间，毫秒级时间戳
     */
    @Schema(description = "称重时间，毫秒级时间戳")
    private Long dataTime;
}
