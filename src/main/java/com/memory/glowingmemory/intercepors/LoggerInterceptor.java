package com.memory.glowingmemory.intercepors;

import com.memory.glowingmemory.utils.common.Constants;
import com.memory.glowingmemory.utils.common.RequestAttributes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author zc
 */
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(Constants.X_REQUEST_ID);
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUID.randomUUID().toString().replace("-", "");
            request.setAttribute(RequestAttributes.REQUEST_ID, requestId);
        }

        String tenantId = request.getHeader(RequestAttributes.TENANT_ID);
        if (StringUtils.isEmpty(tenantId)) {
            tenantId = "1";
            request.setAttribute(RequestAttributes.TENANT_ID, tenantId);
        }

        MDC.put(Constants.X_REQUEST_ID, requestId);
        MDC.put(Constants.X_TENANT_ID, tenantId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constants.X_REQUEST_ID);
        MDC.remove(Constants.X_TENANT_ID);
    }
}