package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.constant.Constants;
import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.common.RestResponse;
import cn.edu.xidian.aws.pojo.vo.user.*;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akynazh@gmail.com
 * @date 2025/1/13
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<UserVO> addUser(@RequestBody UserRegisterVO vo) {
        User user = userService.addUser(vo);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @PutMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<UserVO> updateUser(@RequestBody UserUpdateVO vo) {
        User user = userService.getUser(vo.getUid());
        User updatedUser = userService.updateUser(vo, user);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(updatedUser));
    }

    @PutMapping("/me")
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<UserVO> updateMe(@RequestBody UserUpdateMeVO vo, HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        User updatedUser = userService.updateMe(vo, user);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(updatedUser));
    }

    @GetMapping
    @PreAuthorize(Constants.PRE_AUTHORIZE_EMPLOYEE)
    public RestResponse<UserVO> getMe(HttpServletRequest request) {
        User user = userService.getUser(jwtService.extractUsername(jwtService.extractToken(request)));
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @GetMapping("/{uid}")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<UserVO> getEmployee(@PathVariable String uid) {
        User user = userService.getUser(uid);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @GetMapping("/list")
    @PreAuthorize(Constants.PRE_AUTHORIZE_ADMIN)
    public RestResponse<List<UserVO>> getUsers(@RequestParam(defaultValue = "0") int page) {
        Page<User> usersPage = userService.getUsers(page, 10);
        List<User> users = usersPage.getContent();
        List<UserVO> userVOS = users.stream().map(User::toUserVO).collect(Collectors.toList());
        return new RestResponse<>(HttpStatus.OK, userVOS);
    }

    @PostMapping("/login")
    public RestResponse<String> login(@RequestBody UserLoginVO vo) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(vo.getUid(), vo.getPassword())
        );
        if (auth.isAuthenticated()) {
            return new RestResponse<>(HttpStatus.OK, jwtService.generateToken(vo.getUid()));
        }
        return new RestResponse<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect("/index");
    }
}
