package cn.edu.xidian.aws.pojo.vo.user;

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
@Schema(name = "UserLoginVO", description = "用户登录表单")
public class UserLoginVO {
    @Schema(description = "用户编号")
    private String uid;
    @Schema(description = "密码")
    private String password;
}
