package cn.edu.xidian.aws.pojo.vo.user;

import cn.edu.xidian.aws.constant.Constants;
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
public class UserRegisterVO {
    private String uid;
    private String name;
    private String roles = Constants.USER_ROLE_EMPLOYEE;
}
