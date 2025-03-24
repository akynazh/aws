package cn.edu.xidian.aws.exception;

import lombok.Getter;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Getter
public class AwsNetworkException extends RuntimeException {
    private static final String messagePrefix = "[网络异常]: ";
    private final String message;

    public final static String IMAGE_REC_ERR = "图片识别失败";
    public final static String REQ_ERR = "第三方服务请求失败";


    public AwsNetworkException(String message) {
        this.message = messagePrefix + message;
    }
}