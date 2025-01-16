package cn.edu.xidian.aws.constant;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Getter
public enum ScaleUnit {
    MG(0, "mg"),
    G(1, "g"),
    KG(2, "kg"),
    T(3, "t"),
    LB(4, "lb"),
    OZ(5, "oz"),
    CT(6, "ct");

    private final int code;
    private final String message;

    ScaleUnit(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
