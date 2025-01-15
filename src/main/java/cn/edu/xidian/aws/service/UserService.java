package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.UserRegisterVO;
import cn.edu.xidian.aws.pojo.vo.UserUpdateMeVO;
import cn.edu.xidian.aws.pojo.vo.UserUpdateVO;
import cn.edu.xidian.aws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public User addEmployee(UserRegisterVO vo) {
        User user = new User();
        user.setUid(vo.getUid());
        user.setName(vo.getName());
        user.setPassword(encoder.encode("123456"));
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(1);
        user.setRoles("ROLE_EMPLOYEE");
        return userRepository.save(user);
    }

    public User updateMe(UserUpdateMeVO vo, User originUser) {
        if (StringUtils.hasText(vo.getName())) {
            originUser.setName(vo.getName());
        }
        if (StringUtils.hasText(vo.getPassword())) {
            originUser.setPassword(encoder.encode(vo.getPassword()));
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(originUser);
    }

    public User updateUser(UserUpdateVO vo, User originUser) {
        if (vo == null || originUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (StringUtils.hasText(vo.getUid())) {
            originUser.setUid(vo.getUid());
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
        return userRepository.save(originUser);
    }

    public User getUserByUid(String uid) {
        return userRepository.findByUid(uid).orElse(null);
    }

    public Page<User> getUsers(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr);
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
