package cn.edu.xidian.aws.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/3/24
 * @description
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PredictResultBody {
    private Long clazz;
    private Double score;
    private String name;
}