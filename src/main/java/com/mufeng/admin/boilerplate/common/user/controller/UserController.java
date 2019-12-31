package com.mufeng.admin.boilerplate.common.user.controller;

import com.mufeng.admin.boilerplate.common.user.model.dto.UserUpdateParam;
import com.mufeng.admin.boilerplate.common.util.http.IpUtil;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import com.mufeng.admin.boilerplate.common.user.exception.UserNotExistException;
import com.mufeng.admin.boilerplate.common.user.model.dto.AddUserParam;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserLoginParam;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import com.mufeng.admin.boilerplate.common.user.service.impl.UserPasswordServiceImpl;
import com.mufeng.admin.boilerplate.common.user.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private UserPasswordServiceImpl userPasswordService;

    @PostMapping("/login")
    public Result login(@Valid @RequestBody UserLoginParam userLoginParam, HttpServletRequest request) {
        Optional<User> optionalUser = userService.getByUsername(userLoginParam.getUsername());
        optionalUser.orElseThrow(UserNotExistException::new);
        User user = optionalUser.get();
        Long uid = user.getId();
        // 校验密码
        userPasswordService.verifyPassword(uid, userLoginParam.getPassword());
        // 获取token
        String token = userService.getAccessToken(uid);
        // 更新用户状态
        userService.loginUpdate(uid, token, IpUtil.getIp(request));
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", user.getUsername());
        userInfo.put("token", token);
        userInfo.put("time", LocalDateTime.now());
        return Result.success(userInfo);
    }

    @PostMapping("/add")
    public Result addUser(@Valid @RequestBody AddUserParam addUserParam) {
        userService.addUser(addUserParam.getUsername(), addUserParam.getPassword());
        return Result.success();
    }

    @PutMapping("/{uid}")
    public Result update(@PathVariable(name = "uid") Long uid, @Valid @RequestBody UserUpdateParam userUpdateParam) {
        userService.updateUser(uid, userUpdateParam);
        return Result.success();
    }
}
