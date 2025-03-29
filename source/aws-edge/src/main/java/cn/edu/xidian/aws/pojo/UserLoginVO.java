package cn.edu.xidian.aws.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginVO {
    /**
     * uid
     */
    private String username;
    private String password;
}
