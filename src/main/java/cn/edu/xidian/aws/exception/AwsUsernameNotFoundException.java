package cn.edu.xidian.aws.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/16
 * @description
 */
public class AwsUsernameNotFoundException extends UsernameNotFoundException {
    public AwsUsernameNotFoundException() {
        super("[AwsUsernameNotFoundException] username not found");
    }
}
