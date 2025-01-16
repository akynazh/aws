package cn.edu.xidian.aws.pojo.vo.produce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProduceUpdateVO {
    private long id;
    private String name;
    private int status = -1;
}
