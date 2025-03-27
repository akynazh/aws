package cn.edu.xidian.aws.pojo.vo.user;

import cn.edu.xidian.aws.constant.UserRole;
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
@Schema(name = "UserRegisterVO", description = "用户注册表单")
public class UserRegisterVO {
    @Schema(description = "用户编号")
    private String uid;
    @Schema(description = "员工姓名")
    private String name;
    @Schema(description = "角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN")
    private String roles;
    @Schema(description = "密码")
    private String password;
}
