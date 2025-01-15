package cn.edu.xidian.aws.pojo.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/15
 * @description
 */
@Setter
@Getter
public class RestResponse<T> {
    private int code;
    private String msg;
    private T data;

    public RestResponse() {
    }

    public RestResponse(HttpStatus status) {
        this.code = status.value();
        this.msg = status.getReasonPhrase();
    }

    public RestResponse(HttpStatus status, T data) {
        this.code = status.value();
        this.msg = status.getReasonPhrase();
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseVO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
