package cn.edu.xidian.aws.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum ScaleUnit {
    MG(0, "mg", 1),
    G(1, "g", 1_000),
    KG(2, "kg", 1_000_000),
    T(3, "t", 1_000_000_000),
    LB(4, "lb", 453_592), // 1 磅（lb）= 453.59237 克（g）
    OZ(5, "oz", 28_349), // 1 盎司（oz）= 28.3495 克（g）
    CT(6, "ct", 200); // 1 克拉（ct）= 0.2 克（g）

    private final int code;
    private final String message;
    private final long times;

    ScaleUnit(int code, String message, long times) {
        this.code = code;
        this.message = message;
        this.times = times;
    }

    public static boolean codeExists(int code) {
        return Arrays.stream(UserStatus.values()).anyMatch(userStatus -> userStatus.getCode() == code);
    }

    public static ScaleUnit valueOf(int code) {
        return Arrays.stream(ScaleUnit.values()).filter(scaleUnit -> scaleUnit.getCode() == code).findFirst().orElse(null);
    }
}
