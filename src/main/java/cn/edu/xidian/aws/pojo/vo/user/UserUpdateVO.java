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
@Schema(name = "UserUpdateVO", description = "更新用户信息表单")
public class UserUpdateVO {
    @Schema(description = "用户编号")
    private String uid;
    @Schema(description = "用户姓名")
    private String name;
    @Schema(description = "密码")
    private String password;
    @Schema(description = "状态，0 为禁用，1 为启用，2 为已删除")
    private int status = -1;
}
