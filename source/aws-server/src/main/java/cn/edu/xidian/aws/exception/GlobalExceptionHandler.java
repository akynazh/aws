package cn.edu.xidian.aws.exception;

import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void anyException() {
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void authenticationException() {
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void authorizationDeniedException() {
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void jdbcException() {
    }

    @ExceptionHandler(AwsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String awsNotFoundException(AwsNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AwsArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String awsArgumentException(AwsArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AwsForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String awsForbiddenException(AwsForbiddenException e) {
        return e.getMessage();
    }

    @ExceptionHandler(AwsNetworkException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String awsNetworkException(AwsNetworkException e) {
        return e.getMessage();
    }
}
