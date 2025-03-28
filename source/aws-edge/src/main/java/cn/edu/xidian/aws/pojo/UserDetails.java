package cn.edu.xidian.aws.pojo;

import cn.edu.xidian.aws.constant.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final String uid;
    private final String password;
    private final List<GrantedAuthority> authorities;

    private String cid;

    public UserDetails(User user) {
        this.uid = user.getUid();
        this.password = user.getPassword();
        this.authorities = Stream.of(user.getRoles().split(Constants.ROLE_SPLITTER))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }
}
