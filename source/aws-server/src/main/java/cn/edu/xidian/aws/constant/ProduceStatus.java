package cn.edu.xidian.aws.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum ProduceStatus {
    DISABLED(0, "未种植"),
    ENABLE(1, "在种植"),
    DELETED(2, "已删除");

    private final int code;
    private final String message;

    ProduceStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean codeExists(int code) {
        return Arrays.stream(UserStatus.values()).anyMatch(userStatus -> userStatus.getCode() == code);
    }
}
