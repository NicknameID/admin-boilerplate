package com.mufeng.admin.boilerplate.common.user.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mufeng.admin.boilerplate.common.components.JwtTokenOperator;
import com.mufeng.admin.boilerplate.common.interceptor.InterceptorUtil;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import com.mufeng.admin.boilerplate.common.constant.ConfigConst;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import com.mufeng.admin.boilerplate.common.user.exception.InvalidTokenException;
import com.mufeng.admin.boilerplate.common.user.exception.UserNotExistException;
import com.mufeng.admin.boilerplate.common.user.model.dto.AddUserParam;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserLoginParam;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserUpdateParam;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import com.mufeng.admin.boilerplate.common.user.service.UserService;
import com.mufeng.admin.boilerplate.common.util.http.IpUtil;
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
    private ConfigService configService;
    @Resource
    private JwtTokenOperator jwtTokenOperator;
    @Resource
    private InterceptorUtil interceptorUtil;

    @PostMapping("/login")
    public Result login(@Valid @RequestBody UserLoginParam userLoginParam, HttpServletRequest request) {
        final String username = userLoginParam.getUsername();
        final String passowrd = userLoginParam.getPassword();
        Optional<User> optionalUser = userService.getByUsername(username);
        optionalUser.orElseThrow(UserNotExistException::new);
        final User user = optionalUser.get();
        Long uid = user.getId();
        String token = userService.login(username, passowrd);
        // 更新用户状态
        userService.loginUpdate(uid, token, IpUtil.getIp(request));
        return Result.success(buildLoginInfo(user, token));
    }

    private Map<String, Object> buildLoginInfo(User user, String token) {
        Map<String, Object> loginInfo = new HashMap<>();
        int expires = configService.get(ConfigConst.LoginExpire, Integer.class);
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(expires);
        user.setPassword(null);
        loginInfo.put("user", user);
        loginInfo.put("token", token);
        loginInfo.put("time", LocalDateTime.now());
        loginInfo.put("expiresAt", expiresAt);
        return loginInfo;
    }

    @PostMapping("/refresh")
    public Result refresh(HttpServletRequest request) {
        final String token = interceptorUtil.getToke(request);
        if (StringUtils.isEmpty(token)) throw new InvalidTokenException();
        boolean canRefresh = jwtTokenOperator.canTokenBeRefreshed(token);
        if (!canRefresh) throw new InvalidTokenException();
        final String newToken = jwtTokenOperator.refreshToken(token);
        Optional<User> user = userService.getUserByToken(token);
        user.orElseThrow(InvalidTokenException::new);
        return Result.success(buildLoginInfo(user.get(), newToken));
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
