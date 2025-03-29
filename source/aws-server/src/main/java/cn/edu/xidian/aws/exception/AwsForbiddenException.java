package cn.edu.xidian.aws.exception;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Getter
public class AwsForbiddenException extends RuntimeException {
    private static final String messagePrefix = "[操作禁止] ";
    private final String message;

    public final static String WORK_ALREADY_HAS_PRODUCE_WORK = "系统已经存在对该果实的作业";
    public final static String USER_ROLE_NOT_ALLOWED_TO_LOGIN = "该用户角色不允许登录";

    public AwsForbiddenException(String message) {
        this.message = messagePrefix + message;
    }
}