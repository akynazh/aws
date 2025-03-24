package cn.edu.xidian.aws.exception;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Getter
public class AwsNotFoundException extends RuntimeException {
    private static final String messagePrefix = "[目标不存在]: ";
    private final String message;

    public final static String ITEM_NOT_FOUND = "对象不存在";
    public final static String WORK_NOT_FOUND = "作业不存在";
    public final static String SCALE_NOT_FOUND = "电子秤不存在";
    public final static String USER_NOT_FOUND = "用户不存在";
    public final static String PRODUCE_NOT_FOUND = "果实不存在";
    public static final String RECORD_NOT_FOUND = "称重记录不存在";
    public final static String USER_AUTH_ERROR = "用户认证失败";


    public AwsNotFoundException(String message) {
        this.message = messagePrefix + message;
    }
}