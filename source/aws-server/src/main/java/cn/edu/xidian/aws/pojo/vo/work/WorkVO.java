package cn.edu.xidian.aws.pojo.vo.work;

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
public class WorkVO {

    private Long id;

    /**
     * 果实编号
     */
    private Long produceId;
    /**
     * 采摘开始时间，毫秒级时间戳
     */
    private Long startTime;
    /**
     * 采摘结束时间，毫秒级时间戳
     */
    private Long endTime;
    /**
     * 总的采摘称重结果
     */
    private BigDecimal dataValue;
    /**
     * 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private Integer unit;
    /**
     * 创建时间，毫秒级时间戳
     */
    private Long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private Long updateTime;
    /**
     * 状态，0 为未开始，1 为进行中，2 为已结束，3 为已取消，4 为已删除
     */
    private Integer status;
}
