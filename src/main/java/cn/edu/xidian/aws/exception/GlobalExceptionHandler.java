package cn.edu.xidian.aws.exception;

import cn.edu.xidian.aws.pojo.vo.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
