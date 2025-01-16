package cn.edu.xidian.aws.constant;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
public interface Constants {
    String AWS_NAME = "Agricultural Weighing System";
    String AWS_FARM_NAME = "XDU FARM";

    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    int TOKEN_VALID_TIME_MS = 1000 * 60 * 30;

    String USER_ROLE_ADMIN = "ROLE_ADMIN";
    String USER_ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    String USER_PASSWORD_DEFAULT = "123456";
    String USER_PASSWORD_SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    String PRE_AUTHORIZE_ADMIN = "hasAuthority('" + USER_ROLE_ADMIN + "')";
    String PRE_AUTHORIZE_EMPLOYEE = "hasAuthority('" + USER_ROLE_EMPLOYEE + "')";

    String[] ROUTE_PERMIT = {"/", "/index", "/user/login", "/user/logout"};
    String[] ROUTE_ADMIN = {"/admin/**"};
}
