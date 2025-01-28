package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.constant.UserStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.user.UserRegisterVO;
import cn.edu.xidian.aws.pojo.vo.user.UserUpdateMeVO;
import cn.edu.xidian.aws.pojo.vo.user.UserUpdateVO;
import cn.edu.xidian.aws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
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
    @Value("${aws.admin.uid}")
    private String adminUID;
    @Value("${aws.admin.password}")
    private String adminPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUid(username);
        return user.map(cn.edu.xidian.aws.pojo.bo.UserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User addUser(UserRegisterVO vo) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        User user = new User();
        user.setUid(vo.getUid());
        user.setName(vo.getName());
        user.setPassword(encoder.encode(Constants.USER_PASSWORD_DEFAULT));
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.ENABLE.getCode());
        user.setRoles(vo.getRoles());
        return userRepository.save(user);
    }

    public User updateUser(UserUpdateVO vo, User originUser) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        if (originUser == null) {
            throw new AwsNotFoundException();
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
        if (vo.getStatus() != -1 && UserStatus.codeExists(vo.getStatus())) {
            originUser.setStatus(vo.getStatus());
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(originUser);
    }

    public User updateMe(UserUpdateMeVO vo, User originUser) {
        if (vo == null) {
            throw new AwsArgumentException();
        }
        if (originUser == null) {
            throw new AwsNotFoundException();
        }
        if (StringUtils.hasText(vo.getName())) {
            originUser.setName(vo.getName());
        }
        if (StringUtils.hasText(vo.getPassword())) {
            originUser.setPassword(encoder.encode(vo.getPassword()));
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(originUser);
    }

    public User getUser(String uid) {
        return userRepository.findByUid(uid).orElseThrow(AwsNotFoundException::new);
    }

    public List<User> getUsers(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).getContent();
    }

    public void initAdmin() {
        User user = new User();
        user.setUid(adminUID);
        user.setName(adminUID);
        user.setPassword(encoder.encode(adminPassword));
        user.setRoles(Constants.USER_ROLE_ADMIN + "," + Constants.USER_ROLE_EMPLOYEE);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.ENABLE.getCode());
        if (userRepository.findByUid(adminUID).isPresent()) {
            return;
        }
        userRepository.save(user);
    }
}
