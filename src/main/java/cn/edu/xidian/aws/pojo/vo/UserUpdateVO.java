package cn.edu.xidian.aws.pojo.vo;

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
public class UserUpdateVO {
    private String uid;
    private String cid;
    private String name;
    private String password;
    private int status;
}
