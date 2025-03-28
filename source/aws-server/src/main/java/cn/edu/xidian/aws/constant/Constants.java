package cn.edu.xidian.aws.constant;

import org.springframework.stereotype.Component;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
public interface Constants {
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String USER_PASSWORD_DEFAULT = "123456";

    String PRE_AUTHORIZE_ADMIN = "hasAuthority('ROLE_ADMIN')";
    String PRE_AUTHORIZE_SCALE = "hasAuthority('ROLE_SCALE')";
    String PRE_AUTHORIZE_EMPLOYEE = "hasAuthority('ROLE_EMPLOYEE') or hasAuthority('ROLE_ADMIN')";

    String[] ROUTE_PERMIT = {"/", "/index",
            "/user/login", "/user/logout", "/user/emqx/auth",
            "/docs", "/docs/**",
//            "/swagger-ui.html", "/swagger-ui/**",
    };
    String[] ROUTE_ADMIN = {"/admin/**"};

    String ROLE_SPLITTER = ",";
}
