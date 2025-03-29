package cn.edu.xidian.aws.exception;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Getter
public class AwsArgumentException extends RuntimeException {
    private static final String messagePrefix = "[参数异常] ";
    private final String message;

    public final static String ARGUMENT_NULL = "参数为空";
    public final static String STATUS_PRODUCE_NOT_EXISTS = "果实状态不存在";
    public final static String STATUS_WORK_NOT_EXISTS = "作业状态不存在";
    public final static String STATUS_USER_NOT_EXISTS = "用户状态不存在";
    public final static String STATUS_SCALE_NOT_EXISTS = "电子秤状态不存在";
    public final static String USER_ROLE_NOT_EXISTS = "用户角色不存在";
    public final static String SCALE_UNIT_NOT_EXISTS = "电子秤称重单位不存在";
    public final static String PARAM_MISSING = "缺少参数";
    public final static String TIME_ERROR = "时间参数错误";

    public AwsArgumentException(String message) {
        this.message = messagePrefix + message;
    }
}
