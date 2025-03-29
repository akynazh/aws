package cn.edu.xidian.aws.constant;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
public interface Security {
    String TOKEN_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String[] ROUTE_PERMIT = {"/user/auth", "/weigh/record"
    };
}
