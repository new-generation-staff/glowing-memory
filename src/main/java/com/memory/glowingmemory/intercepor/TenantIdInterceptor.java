package com.memory.glowingmemory.intercepor;

import com.memory.glowingmemory.util.common.Constants;
import com.memory.glowingmemory.util.common.RequestAttributes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;


/**
 * @author zc
 */
@Component
@Slf4j
public class TenantIdInterceptor implements HandlerInterceptor {

    //todo 校验 session 的和 RequestAttributes 的校验分开
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestId = request.getHeader(Constants.X_REQUEST_ID);
        if (StringUtils.isBlank(requestId)) {
            requestId = UUID.randomUUID().toString().replace("-", "");
        }
        request.setAttribute(RequestAttributes.REQUEST_ID, requestId);

        String tenantId = request.getHeader(Constants.X_TENANT_ID);
        if (StringUtils.isBlank(tenantId)) {
            tenantId = "1";
        }
        request.setAttribute(RequestAttributes.TENANT_ID, tenantId);

        MDC.put(Constants.X_REQUEST_ID, requestId);
        MDC.put(Constants.X_TENANT_ID, tenantId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
