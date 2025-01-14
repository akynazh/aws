package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.UserLoginVO;
import cn.edu.xidian.aws.pojo.vo.UserRegisterVO;
import cn.edu.xidian.aws.pojo.vo.UserUpdateMeVO;
import cn.edu.xidian.aws.pojo.vo.UserUpdateVO;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<User> getMe(HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<User> updateMe(@RequestBody UserUpdateMeVO vo, HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        userService.updateMe(vo, user);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@RequestBody UserUpdateVO vo) {
        if (vo == null) {
            throw new IllegalArgumentException();
        }
        User user = userService.getUser(vo.getUid());
        if (user == null) {
            throw new BadCredentialsException("user not found");
        }
        userService.updateUser(vo, user);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> addEmployee(@RequestBody UserRegisterVO vo) {
        User user = new User();
        user.setUid(vo.getUid());
//        user.setCid(vo.getCid());
        user.setName(vo.getName());
        user.setPassword("123456");
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        user.setStatus(1);
        user.setRoles("ROLE_EMPLOYEE");
        userService.addUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginVO vo) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(vo.getUid(), vo.getPassword())
        );
        if (auth.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(vo.getUid()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public ResponseEntity<User> profile(HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        return ResponseEntity.ok(user);
    }
}
