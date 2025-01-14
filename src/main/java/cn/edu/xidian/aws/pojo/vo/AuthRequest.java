package cn.edu.xidian.aws.pojo.vo;

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
public class AuthRequest {
    private String uid;
    private String password;
}
