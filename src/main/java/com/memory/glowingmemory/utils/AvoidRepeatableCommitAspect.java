package com.memory.glowingmemory.utils;

import com.memory.glowingmemory.interfaces.AvoidRepeatableCommit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.data.redis.core.RedisTemplate;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author zc
 */
@Aspect
@Component
public class AvoidRepeatableCommitAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /*@annotation中是AvoidRepeatableCommit接口的全路径
     * */

    //todo 增强判断参数是否相同
    @Around("@annotation(com.memory.glowingmemory.interfaces.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s",className,name);
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s_%d",ip,hashCode);
        AvoidRepeatableCommit avoidRepeatableCommit =  method.getAnnotation(AvoidRepeatableCommit.class);
        long timeout = avoidRepeatableCommit.timeout();
        if (timeout < 0){
            timeout = 5000;
        }
        String value = redisTemplate.opsForValue().get(key);
        if (value!=null){
            return "请勿重复点击";
        }else {
            //key 为判断重复的条件，具体根据业务需求
            //stringRedisTemplate.opsForValue().set()方法不设置最后一个参数(TimeUnit.SECONDS)时,value会无法存入正确的值
            redisTemplate.opsForValue().set(key, "存在了",timeout, TimeUnit.MILLISECONDS);
        }
        //执行方法
        Object object = point.proceed();
        return object;
    }
}
