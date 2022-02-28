package com.memory.glowingmemory.intercepors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author zc
 */
@Component
@Slf4j
public class TenantIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String x_tenant_id = request.getHeader("x_tenant_id");
        if (x_tenant_id == null) {
            log.warn("x_tenant_id is null");
            PrintWriter out = response.getWriter();
            out.append("x_tenant_id is null");
            return false;
        }
        return true;
    }

}
