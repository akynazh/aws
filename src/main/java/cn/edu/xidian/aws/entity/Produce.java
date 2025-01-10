package cn.edu.xidian.aws.entity;

import jakarta.persistence.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 农作物
 */
@Entity
@Table(name = "t_produce")
public class Produce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 农作物名称
     */
    @Column(unique = true)
    private String name;
    /**
     * 农作物类型
     */
    @Column(unique = true)
    private int type;
    /**
     * 创建时间，毫秒级时间戳
     */
    private long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private long updateTime;
    /**
     * 状态，0 为未种植，1 为在种植，2 为已删除
     */
    private int status;

    public Produce() {
    }

    public Produce(long id, String name, int type, long createTime, long updateTime, int status) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "Produce{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
