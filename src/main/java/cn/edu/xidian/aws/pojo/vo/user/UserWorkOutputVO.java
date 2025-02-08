package cn.edu.xidian.aws.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author akynazh@gmail.com
 * @date 2/8/25
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserWorkOutputVO {
    private String name;
    private Long workId;
    private String produceName;
    private BigDecimal dataValue;
    private String unit;
}
