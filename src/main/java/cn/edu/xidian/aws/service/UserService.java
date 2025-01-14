package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.UserUpdateMeVO;
import cn.edu.xidian.aws.pojo.vo.UserUpdateVO;
import cn.edu.xidian.aws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@Service
@Primary
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUid(username);
        return user.map(cn.edu.xidian.aws.pojo.bo.UserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateMe(UserUpdateMeVO vo, User originUser) {
        if (StringUtils.hasText(vo.getName())) {
            originUser.setName(vo.getName());
        }
        if (StringUtils.hasText(vo.getPassword())) {
            originUser.setPassword(encoder.encode(vo.getPassword()));
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        userRepository.save(originUser);
    }

    public void updateUser(UserUpdateVO vo, User originUser) {
        if (StringUtils.hasText(vo.getUid())) {
            originUser.setUid(vo.getUid());
        }
        if (StringUtils.hasText(vo.getCid())) {
            originUser.setCid(vo.getCid());
        }
        if (StringUtils.hasText(vo.getName())) {
            originUser.setName(vo.getName());
        }
        if (StringUtils.hasText(vo.getPassword())) {
            originUser.setPassword(encoder.encode(vo.getPassword()));
        }
        if (vo.getStatus() != -1) {
            originUser.setStatus(vo.getStatus());
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        userRepository.save(originUser);
    }

    public User getUser(String uid) {
        return userRepository.findByUid(uid).orElse(null);
    }

    public void initAdmin(String uid, String password) {
        User user = new User();
        user.setUid(uid);
        user.setName("admin" + uid);
        user.setPassword(encoder.encode(password));
        user.setRoles("ROLE_ADMIN,ROLE_EMPLOYEE");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(1);
        if (userRepository.findByUid(uid).isPresent()) {
            return;
        }
        userRepository.save(user);
    }
}
