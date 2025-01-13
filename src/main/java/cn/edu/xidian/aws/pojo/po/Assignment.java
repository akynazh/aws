package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 采摘作业分配
 */
@Entity
@Table(name = "t_assignment", indexes = {
        @Index(columnList = "workId"),
        @Index(columnList = "employeeId")
})
public class Assignment {

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

    public Assignment() {
    }

    public Assignment(long id, long workId, long employeeId, long createTime, long updateTime, int status) {
        this.id = id;
        this.workId = workId;
        this.employeeId = employeeId;
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
        return "Assignment{" +
                "id=" + id +
                ", workId=" + workId +
                ", employeeId=" + employeeId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", status=" + status +
                '}';
    }
}
