package cn.edu.xidian.aws.pojo.dto;

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
public class UserWorkOutputDTO {
    private String name;
    private Long workId;
    private String produceName;
    private BigDecimal dataValue;
    private Integer unit;
}
