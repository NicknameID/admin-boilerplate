package tech.mufeng.admin.boilerplate.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.mufeng.admin.boilerplate.common.constant.UserSessionField;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    private HttpSession httpSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean active = (Boolean)httpSession.getAttribute(UserSessionField.ACTIVE);
        if (!BooleanUtils.toBooleanDefaultIfNull(active, false)) {
            CustomExceptionEnum.NOT_LOGIN_EXCEPTION.throwException();
        }
        return true;
    }
}
