package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.scale.ScaleVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 电子秤
 */
@Entity
@Table(name = "t_scale")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * mqtt user name (uuid)
     */
    private String sid;
    /**
     * 电子秤密钥 mqtt client password
     */
    @Column(unique = true)
    private String skey;
    /**
     * 型号
     */
    private String model;
    /**
     * 最大量程
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal maxCapacity;
    /**
     * 最小量程
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal minCapacity;
    /**
     * 量程单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private Integer unit;
    /**
     * 检定分度值
     */
    private Integer verificationInterval;
    /**
     * 显示分度值
     */
    private Integer displayInterval;
    /**
     * 分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private Integer unitDv;
    /**
     * 通信协议，0 为 MQTT，1 为 HTTP, 2 为 CoAP, 3 为 STOMP
     */
//    private Integer protocol;
    /**
     * 创建时间，毫秒级时间戳
     */
    private Long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private Long updateTime;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    private Integer status;

    public static ScaleVO toScaleVO(Scale scale) {
        if (scale == null) {
            return null;
        }
        ScaleVO vo = new ScaleVO();
        BeanUtils.copyProperties(scale, vo);
        return vo;
    }
}
