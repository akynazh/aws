package cn.edu.xidian.aws.constant;

import cn.edu.xidian.aws.pojo.UserAuthResult;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2/6/25
 * @description
 */
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN", "管理员"),
    EMPLOYEE("ROLE_EMPLOYEE", "员工"),
    SCALE("ROLE_SCALE", "电子秤"),
    SYS("ROLE_SYS", "系统用户");

    private final String code;
    private final String name;
    public static final String LABEL = "用户角色";
    public static final String SPLITTER = ",";

    UserRole(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean codeExists(String code) {
        return Arrays.stream(UserRole.values()).anyMatch(role -> role.getCode().equals(code));
    }

    public static Optional<UserRole> getUserRoleFromCode(String code) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.getCode().equals(code))
                .findFirst();
    }

    public static List<UserRole> getRolesFromCodesString(String roles) {
        return Arrays.stream(roles.split(SPLITTER))
                .map(UserRole::getUserRoleFromCode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public static boolean canAccessMqtt(String codesString) {
        List<UserRole> roles = UserRole.getRolesFromCodesString(codesString);
        for (UserRole role : roles) {
            String code = role.getCode();
            if (StringUtils.hasText(code) && (code.equals(SCALE.getCode()) || code.equals(SYS.getCode()))) {
                return true;
            }
        }
        return false;
    }
}
