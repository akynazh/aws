package cn.edu.xidian.aws.constants;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum ScaleStatus {
    DISABLED(0, "禁用"),
    ENABLE(1, "启用"),
    DELETED(2, "已删除");

    private final int code;
    private final String message;

    ScaleStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
