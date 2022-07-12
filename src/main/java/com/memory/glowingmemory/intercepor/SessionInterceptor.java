package com.memory.glowingmemory.intercepor;

import com.memory.glowingmemory.util.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author zc
 */
@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(Constants.FONT_SESSION) == null) {
            log.warn("session is null, 未登录");
            response.sendRedirect("/redirect/login");
            return false;
        }
        return true;
    }
}
