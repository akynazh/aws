package cn.edu.xidian.aws.pojo.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 农作物
 */
@Entity
@Table(name = "t_produce")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
