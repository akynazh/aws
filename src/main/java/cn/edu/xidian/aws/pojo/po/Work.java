package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.work.WorkVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 采摘作业
 */
@Entity
@Table(name = "t_work", indexes = {
        @Index(columnList = "produceId")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 农作物编号
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
    @Column(precision = 10, scale = 2)
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

    public static WorkVO toWorkVO(Work work) {
        if (work == null) {
            return null;
        }
        WorkVO workVO = new WorkVO();
        BeanUtils.copyProperties(work, workVO);
        return workVO;
    }
}
