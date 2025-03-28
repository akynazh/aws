package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.constant.UserRole;
import cn.edu.xidian.aws.constant.UserStatus;
import cn.edu.xidian.aws.exception.AwsArgumentException;
import cn.edu.xidian.aws.exception.AwsForbiddenException;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.user.UserRegisterVO;
import cn.edu.xidian.aws.pojo.vo.user.UserUpdateMeVO;
import cn.edu.xidian.aws.pojo.vo.user.UserUpdateVO;
import cn.edu.xidian.aws.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        String roles = vo.getRoles();
        String uid = vo.getUid();
        String name = vo.getName();
        String password = vo.getPassword();
        if (!StringUtils.hasText(uid) || !StringUtils.hasText(name)) {
            throw new AwsArgumentException(AwsArgumentException.PARAM_MISSING);
        }

        List<UserRole> roleList = UserRole.getRolesFromCodesString(roles);
        if (CollectionUtils.isEmpty(roleList)) {
            roles = UserRole.EMPLOYEE.getCode();
        }
        if (password == null) {
            password = Constants.USER_PASSWORD_DEFAULT;
        }

        User user = new User();
        user.setUid(uid);
        user.setName(name);
        user.setPassword(encoder.encode(password));
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.ENABLED.getCode());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(UserUpdateVO vo, User originUser) {
        if (vo == null || originUser == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (vo.getStatus() != null && !UserStatus.codeExists(vo.getStatus())) {
            throw new AwsArgumentException(AwsArgumentException.STATUS_USER_NOT_EXISTS);
        }
        if (StringUtils.hasText(vo.getRoles())) {
            List<UserRole> roleList = UserRole.getRolesFromCodesString(vo.getRoles());
            if (CollectionUtils.isEmpty(roleList)) {
                throw new AwsArgumentException(AwsArgumentException.USER_ROLE_NOT_EXISTS);
            }
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
        if (vo.getStatus() != null && UserStatus.codeExists(vo.getStatus())) {
            originUser.setStatus(vo.getStatus());
        }
        if (StringUtils.hasText(vo.getRoles())) {
            originUser.setRoles(vo.getRoles());
        }
        originUser.setUpdateTime(System.currentTimeMillis());
        return userRepository.save(originUser);
    }

    public User updateMe(UserUpdateMeVO vo, User originUser) {
        if (vo == null) {
            throw new AwsArgumentException(AwsArgumentException.ARGUMENT_NULL);
        }
        if (originUser == null) {
            throw new AwsNotFoundException(AwsNotFoundException.USER_NOT_FOUND);
        }
        if (UserStatus.userNotEnabled(originUser.getStatus())) {
            throw new AwsForbiddenException(UserStatus.label + UserStatus.fromCode(originUser.getStatus()).getMessage());
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

    public User getUserByUID(String uid) {
        return userRepository.findByUid(uid).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.USER_NOT_FOUND)
        );
    }

    public User getUserByID(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new AwsNotFoundException(AwsNotFoundException.USER_NOT_FOUND)
        );
    }

    public List<User> getUsers(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).getContent();
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public void initAdmin() {
        User user = new User();
        user.setUid(adminUID);
        user.setName(adminUID);
        user.setPassword(encoder.encode(adminPassword));
        user.setRoles(UserRole.ADMIN.getCode() + Constants.ROLE_SPLITTER + UserRole.EMPLOYEE.getCode());
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(UserStatus.ENABLED.getCode());
        if (userRepository.findByUid(adminUID).isPresent()) {
            return;
        }
        userRepository.save(user);
    }

    public Map<Long, String> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream().collect(Collectors.toMap(User::getId, User::getName));
    }
}
