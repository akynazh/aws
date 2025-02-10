package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.record.RecordVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 称重记录
 */
@Entity
@Table(name = "t_record", indexes = {
        @Index(columnList = "workId"),
        @Index(columnList = "employeeId"),
        @Index(columnList = "scaleId"),
        @Index(columnList = "employeeId, workId")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 采摘作业编号
     */
    private Long workId;
    /**
     * 员工编号
     */
    private Long employeeId;
    /**
     * 电子秤编号
     */
    private Long scaleId;
    /**
     * 称重结果
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal dataValue;
    /**
     * 称量结果误差范围，正负多少
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal dataErrorMargin;
    /**
     * 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private Integer unit;
    /**
     * 称重时间，毫秒级时间戳
     */
    private Long dataTime;

    public static RecordVO toRecordVO(Record record) {
        if (record == null) {
            return null;
        }
        RecordVO vo = new RecordVO();
        BeanUtils.copyProperties(record, vo);
        return vo;
    }
}
