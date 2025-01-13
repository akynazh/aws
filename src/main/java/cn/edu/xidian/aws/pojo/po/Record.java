package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;

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
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 采摘作业编号
     */
    private long workId;
    /**
     * 员工编号
     */
    private long employeeId;
    /**
     * 电子秤编号
     */
    private long scaleId;
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
    private int unit;
    /**
     * 称重时间，毫秒级时间戳
     */
    private long dataTime;

    public Record() {
    }

    public Record(long id, long workId, long employeeId, long scaleId, BigDecimal dataValue, BigDecimal dataErrorMargin, int unit, long dataTime) {
        this.id = id;
        this.workId = workId;
        this.employeeId = employeeId;
        this.scaleId = scaleId;
        this.dataValue = dataValue;
        this.dataErrorMargin = dataErrorMargin;
        this.unit = unit;
        this.dataTime = dataTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkId() {
        return workId;
    }

    public void setWorkId(long workId) {
        this.workId = workId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getScaleId() {
        return scaleId;
    }

    public void setScaleId(long scaleId) {
        this.scaleId = scaleId;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public BigDecimal getDataErrorMargin() {
        return dataErrorMargin;
    }

    public void setDataErrorMargin(BigDecimal dataErrorMargin) {
        this.dataErrorMargin = dataErrorMargin;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public long getDataTime() {
        return dataTime;
    }

    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", workId=" + workId +
                ", employeeId=" + employeeId +
                ", scaleId=" + scaleId +
                ", dataValue=" + dataValue +
                ", dataErrorMargin=" + dataErrorMargin +
                ", unit=" + unit +
                ", dataTime=" + dataTime +
                '}';
    }
}
