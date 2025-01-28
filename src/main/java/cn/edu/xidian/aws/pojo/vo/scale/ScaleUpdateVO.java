package cn.edu.xidian.aws.pojo.vo.scale;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScaleUpdateVO {
    private long id;
    /**
     * 状态，0 为禁用，1 为启用，2 为已删除
     */
    private int status;
}
