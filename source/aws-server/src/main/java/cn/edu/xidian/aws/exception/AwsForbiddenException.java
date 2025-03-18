package cn.edu.xidian.aws.exception;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Getter
public class AwsForbiddenException extends RuntimeException {
    private static final String messagePrefix = "FORBIDDEN: ";
    private final String message;

    public AwsForbiddenException(String message) {
        this.message = messagePrefix + message;
    }
}