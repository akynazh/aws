package cn.edu.xidian.aws.service;

import cn.edu.xidian.aws.pojo.User;
import cn.edu.xidian.aws.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUid(username);
        return user.map(cn.edu.xidian.aws.pojo.UserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<User> getUsers(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).getContent();
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public Map<Long, String> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream().collect(Collectors.toMap(User::getId, User::getName));
    }
}
