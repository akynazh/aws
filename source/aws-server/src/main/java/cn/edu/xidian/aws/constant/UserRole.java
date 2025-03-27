package cn.edu.xidian.aws.constant;

import lombok.Getter;
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
    SCALE("ROLE_SCALE", "电子秤");

    private final String code;
    private final String name;
    public static final String label = "用户角色";

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
        return Arrays.stream(roles.split(Constants.ROLE_SPLITTER))
                .map(UserRole::getUserRoleFromCode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
