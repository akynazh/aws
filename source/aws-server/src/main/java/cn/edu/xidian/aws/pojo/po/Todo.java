package cn.edu.xidian.aws.pojo.po;

import cn.edu.xidian.aws.pojo.vo.todo.TodoVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/4/2
 * @description 待处理称重记录
 */
@Entity
@Table(name = "t_todo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RecordAddVO START
    /**
     * 果实 Id
     */
    private Long produceId;

    /**
     * 果实名称
     */
    private String produceName;

    /**
     * 采摘作业图片 URL
     */
    private String image;

    /**
     * 员工编号
     */
    @Column(nullable = false)
    private Long employeeId;

    /**
     * 电子秤编号
     */
    @Column(nullable = false)
    private Long scaleId;

    /**
     * 称重结果
     */
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal dataValue;

    /**
     * 称量结果误差范围，正负多少
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal dataErrorMargin;

    /**
     * 称重单位，0 为 mg，1 为 g，2 为 kg，3 为 t，4 为 lb（磅），5 为 oz（盎司），6 为 ct（克拉）
     */
    @Column(nullable = false)
    private Integer unit;

    /**
     * 称重时间，毫秒级时间戳
     */
    @Column(nullable = false)
    private Long dataTime;

    public TodoVO toTodoVO() {
        TodoVO todoVO = new TodoVO();
        BeanUtils.copyProperties(this, todoVO);
        return todoVO;
    }
}
