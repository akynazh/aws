package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.produce.ProduceVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/10
 * @description 果实
 */
@Entity
@Table(name = "t_produce")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Produce {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 用户自己添加的农作物，id 从 1000000 开始，其他系统自带的从 0 开始（继承于 Predict 系统提供的农作物）
     */
    private Long id;

    /**
     * 果实名称
     */
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * 果实英文名称
     */
    @Column(unique = true)
    private String nameEn;

    /**
     * 创建时间，毫秒级时间戳
     */
    @Column(nullable = false)
    private Long createTime;

    /**
     * 更新时间，毫秒级时间戳
     */
    @Column(nullable = false)
    private Long updateTime;

    /**
     * 状态，0 为未种植，1 为在种植，2 为已删除
     */
    @Column(nullable = false)
    private Integer status;

    public static ProduceVO toProduceVO(Produce produce) {
        if (produce == null) {
            return null;
        }
        ProduceVO produceVO = new ProduceVO();
        BeanUtils.copyProperties(produce, produceVO);
        return produceVO;
    }
}
