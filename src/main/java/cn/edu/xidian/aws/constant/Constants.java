package cn.edu.xidian.aws.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
@Component
public interface Constants {
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";

    String USER_ROLE_ADMIN = "ROLE_ADMIN";
    String USER_ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    String USER_PASSWORD_DEFAULT = "123456";

    String PRE_AUTHORIZE_ADMIN = "hasAuthority('" + USER_ROLE_ADMIN + "')";
    String PRE_AUTHORIZE_EMPLOYEE = "hasAuthority('" + USER_ROLE_EMPLOYEE + "')";

    String[] ROUTE_PERMIT = {"/", "/index", "/user/login", "/user/logout"};
    String[] ROUTE_ADMIN = {"/admin/**"};
}
