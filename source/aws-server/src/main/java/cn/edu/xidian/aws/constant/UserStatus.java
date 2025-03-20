package cn.edu.xidian.aws.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum UserStatus {
    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    DELETED(2, "已删除");

    private final int code;
    private final String message;

    UserStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean codeExists(int code) {
        return Arrays.stream(UserStatus.values()).anyMatch(s -> s.getCode() == code);
    }

    public static UserStatus fromCode(int code) {
        return Arrays.stream(UserStatus.values()).filter(s -> s.getCode() == code).findFirst().orElse(null);
    }

    public static boolean userNotEnabled(int code) {
        return ENABLED.getCode() != code;
    }
}
