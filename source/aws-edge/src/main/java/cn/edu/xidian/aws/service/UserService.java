package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.pojo.UserLoginVO;
import cn.edu.xidian.aws.pojo.UserPO;
import cn.edu.xidian.aws.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@Service
@Primary
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserPO> user = userRepository.findByUid(username);
        return user.map(cn.edu.xidian.aws.pojo.UserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserPO getUserByUID(String uid) {
        return userRepository.findByUid(uid).orElseThrow(() -> new UsernameNotFoundException(uid));
    }

    public boolean authUser(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        ).isAuthenticated();
    }
}
