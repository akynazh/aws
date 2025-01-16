package cn.edu.xidian.aws.exception;

import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public RestResponse<String> anyException(Exception e) {
        RestResponse<String> resp = new RestResponse<>(HttpStatus.INTERNAL_SERVER_ERROR);
        resp.setMsg(e.getLocalizedMessage());
        return resp;
    }

    @ExceptionHandler(AuthenticationException.class)
    public RestResponse<String> authenticationException() {
        return new RestResponse<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public RestResponse<String> authorizationDeniedException() {
        return new RestResponse<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JDBCException.class)
    public RestResponse<String> jdbcException() {
        return new RestResponse<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AwsNotFoundException.class)
    public RestResponse<String> awsItemNotFoundException() {
        return new RestResponse<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AwsArgumentException.class)
    public RestResponse<String> awsArgumentException() {
        return new RestResponse<>(HttpStatus.BAD_REQUEST);
    }
}
