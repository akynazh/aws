package cn.edu.xidian.aws.pojo.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "UserUpdateMeVO", description = "更新个人信息表单")
public class UserUpdateMeVO {
    @Schema(description = "用户姓名")
    private String name;
    @Schema(description = "密码")
    private String password;
}
