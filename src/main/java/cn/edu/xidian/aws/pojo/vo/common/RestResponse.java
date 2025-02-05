package cn.edu.xidian.aws.pojo.vo.common;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "RestResponse<T>", description = "REST 结果")
public class RestResponse<T> {
    @Schema(description = "状态码")
    private int code;
    @Schema(description = "信息")
    private String msg;
    @Schema(description = "数据")
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
