package cn.edu.xidian.aws.pojo.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(name = "UserVO", description = "用户信息")
public class UserVO {
    @Schema(description = "用户 ID")
    private long id;
    @Schema(description = "用户身份证")
    private String uid;
    @Schema(description = "员工姓名")
    private String name;
    @Schema(description = "角色，以英文逗号分隔，比如 ROLE_EMPLOYEE,ROLE_ADMIN")
    private String roles;
    @Schema(description = "创建时间，毫秒级时间戳")
    private long createTime;
    @Schema(description = "更新时间，毫秒级时间戳")
    private long updateTime;
    @Schema(description = "状态，0 为禁用，1 为启用，2 为已删除")
    private int status = -1;
}
