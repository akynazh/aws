package cn.edu.xidian.aws.controller;

import cn.edu.xidian.aws.pojo.po.User;
import cn.edu.xidian.aws.pojo.vo.*;
import cn.edu.xidian.aws.service.JwtService;
import cn.edu.xidian.aws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RestResponse<UserVO> addEmployee(@RequestBody UserRegisterVO vo) {
        User user = userService.addEmployee(vo);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public RestResponse<UserVO> updateMe(@RequestBody UserUpdateMeVO vo, HttpServletRequest request) {
        User user = userService.getUserByUid(jwtService.extractUsername(jwtService.extractToken(request)));
        User updatedUser = userService.updateMe(vo, user);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(updatedUser));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RestResponse<UserVO> updateUser(@RequestBody UserUpdateVO vo) {
        User user = userService.getUserByUid(vo.getUid());
        User updatedUser = userService.updateUser(vo, user);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(updatedUser));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public RestResponse<UserVO> getMe(HttpServletRequest request) {
        User user = userService.getUserByUid(jwtService.extractUsername(jwtService.extractToken(request)));
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @GetMapping("/{uid}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RestResponse<UserVO> getEmployee(@PathVariable String uid) {
        User user = userService.getUserByUid(uid);
        return new RestResponse<>(HttpStatus.OK, User.toUserVO(user));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public RestResponse<List<UserVO>> getEmployeeList(@RequestParam(defaultValue = "0") int page) {
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
