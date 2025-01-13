package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;

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
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 农作物编号
     */
    private long produceId;
    /**
     * 采摘开始时间，毫秒级时间戳
     */
    private long startTime;
    /**
     * 采摘结束时间，毫秒级时间戳
     */
    private long endTime;
    /**
     * 总的采摘称重结果
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal dataValue;
    /**
     * 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private int unit;
    /**
     * 创建时间，毫秒级时间戳
     */
    private long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private long updateTime;
    /**
     * 状态，0 为未开始，1 为进行中，2 为已结束，3 为已取消，4 为已删除
     */
    private int status;

    public Work() {
    }

    public Work(long id, long produceId, long startTime, long endTime, BigDecimal dataValue, int unit, long createTime, long updateTime, int status) {
        this.id = id;
        this.produceId = produceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dataValue = dataValue;
        this.unit = unit;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduceId() {
        return produceId;
    }

    public void setProduceId(long produceId) {
        this.produceId = produceId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDataValue() {
        return dataValue;
    }

    public void setDataValue(BigDecimal dataValue) {
        this.dataValue = dataValue;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id=" + id +
                ", produceId=" + produceId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", dataValue=" + dataValue +
                ", unit=" + unit +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
