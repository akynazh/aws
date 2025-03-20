package cn.edu.xidian.aws.constant;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author akynazh@gmail.com
 * @date 3/20/25
 * @description
 */
@Getter
public enum ScaleProtocol {
    MQTT(0, "Mqtt"),
    HTTP(1, "Http"),
    COAP(2, "CoAP"),
    STOMP(3, "Stomp");


    private final int code;
    private final String protocol;

    ScaleProtocol(int code, String protocol) {
        this.code = code;
        this.protocol = protocol;
    }

    public static boolean codeExists(int code) {
        return Arrays.stream(ScaleProtocol.values()).anyMatch(s -> s.getCode() == code);
    }

    public static ScaleProtocol fromCode(int code) {
        return Arrays.stream(ScaleProtocol.values()).filter(s -> s.getCode() == code).findFirst().orElse(null);
    }
}
