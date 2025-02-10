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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 果实名称
     */
    @Column(unique = true)
    private String name;
    /**
     * 创建时间，毫秒级时间戳
     */
    private Long createTime;
    /**
     * 更新时间，毫秒级时间戳
     */
    private Long updateTime;
    /**
     * 状态，0 为未种植，1 为在种植，2 为已删除
     */
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
