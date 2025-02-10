package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.exception.AwsNotFoundException;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.user.*;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description 用户服务模块
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Tag(name = "用户管理模块")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "管理员添加用户")
    @PostMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<UserVO> addUser(@RequestBody UserRegisterVO vo) {
        User user = userService.addUser(vo);
        return ResponseEntity.ok(User.toUserVO(user));
    }

    @Operation(summary = "管理员更新用户")
    @PutMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<UserVO> updateUser(@RequestBody UserUpdateVO vo) {
        User user = userService.getUser(vo.getUid());
        User updatedUser = userService.updateUser(vo, user);
        return ResponseEntity.ok(User.toUserVO(updatedUser));
    }

    @Operation(summary = "用户更新个人信息")
    @PutMapping("/me")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<UserVO> updateMe(@RequestBody UserUpdateMeVO vo, HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        User updatedUser = userService.updateMe(vo, user);
        return ResponseEntity.ok(User.toUserVO(updatedUser));
    }

    @Operation(summary = "用户获取个人信息")
    @GetMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public ResponseEntity<UserVO> getMe(HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        return ResponseEntity.ok(User.toUserVO(user));
    }

    @Operation(summary = "管理员获取用户信息")
    @GetMapping("/{uid}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<UserVO> getEmployee(@PathVariable String uid) {
        User user = userService.getUser(uid);
        return ResponseEntity.ok(User.toUserVO(user));
    }

    @Operation(summary = "管理员获取用户列表")
    @GetMapping("/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public ResponseEntity<UserListVO> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<User> users = userService.getUsers(page, size);
        long userCount = userService.getUserCount();
        List<UserVO> userVOS = users.stream().map(User::toUserVO).collect(Collectors.toList());
        UserListVO vo = new UserListVO();
        vo.setUserList(userVOS);
        vo.setCount(userCount);
        return ResponseEntity.ok(vo);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginVO vo) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(vo.getUid(), vo.getPassword())
        );
        if (auth.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(vo.getUid()));
        }
        throw new AwsNotFoundException();
    }
}
