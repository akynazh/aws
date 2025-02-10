package cn.edu.xidian.aws.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum WorkStatus {
    NOT_STARTED(0, "未开始"),
    ONGOING(1, "进行中"),
    FINISHED(2, "已结束"),
    CANCELED(3, "已取消"),
    DELETED(4, "已删除");

    private final int code;
    private final String message;

    WorkStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean codeExists(int code) {
        return Arrays.stream(UserStatus.values()).anyMatch(userStatus -> userStatus.getCode() == code);
    }
}
