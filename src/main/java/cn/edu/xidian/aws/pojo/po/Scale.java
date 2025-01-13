package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 电子秤
 */
@Entity
@Table(name = "t_scale")
public class Scale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 电子秤密钥
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
    private int unit;
    /**
     * 检定分度值
     */
    private int verificationInterval;
    /**
     * 显示分度值
     */
    private int displayInterval;
    /**
     * 分度值单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    private int unitDv;
    /**
     * 通信协议，0 为 MQTT，1 为 HTTP
     */
    private int protocol;
    /**
     * 创建时间，毫秒级时间戳
     */
    private long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private long updateTime;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    private int status;

    public Scale() {
    }

    public Scale(long id, String skey, String model, BigDecimal maxCapacity, BigDecimal minCapacity, int unit, int verificationInterval, int displayInterval, int unitDv, int protocol, long createTime, long updateTime, int status) {
        this.id = id;
        this.skey = skey;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.minCapacity = minCapacity;
        this.unit = unit;
        this.verificationInterval = verificationInterval;
        this.displayInterval = displayInterval;
        this.unitDv = unitDv;
        this.protocol = protocol;
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

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public BigDecimal getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(BigDecimal minCapacity) {
        this.minCapacity = minCapacity;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getVerificationInterval() {
        return verificationInterval;
    }

    public void setVerificationInterval(int verificationInterval) {
        this.verificationInterval = verificationInterval;
    }

    public int getDisplayInterval() {
        return displayInterval;
    }

    public void setDisplayInterval(int displayInterval) {
        this.displayInterval = displayInterval;
    }

    public int getUnitDv() {
        return unitDv;
    }

    public void setUnitDv(int unitDv) {
        this.unitDv = unitDv;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
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
        return "Scale{" +
                "id=" + id +
                ", skey='" + skey + '\'' +
                ", model='" + model + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", minCapacity=" + minCapacity +
                ", unit=" + unit +
                ", verificationInterval=" + verificationInterval +
                ", displayInterval=" + displayInterval +
                ", unitDv=" + unitDv +
                ", protocol=" + protocol +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
