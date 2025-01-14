package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
