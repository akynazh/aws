package cn.edu.xidian.aws.pojo.vo.user;

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
    private String uid;
    private String password;
}
