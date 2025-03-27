package cn.edu.xidian.aws.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void anyException(Exception e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String authenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String authorizationDeniedException(AuthorizationDeniedException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(JDBCException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void jdbcException(JDBCException e) {
        log.error(e.getMessage());
    }

    @ExceptionHandler(AwsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String awsNotFoundException(AwsNotFoundException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(AwsArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String awsArgumentException(AwsArgumentException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(AwsForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String awsForbiddenException(AwsForbiddenException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(AwsNetworkException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String awsNetworkException(AwsNetworkException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void messagingException(MessagingException e) {
        log.error(e.getMessage());
    }
}
