package cn.edu.xidian.aws.pojo.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/14
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserUpdateMeVO {
    private String name;
    private String password;
}
