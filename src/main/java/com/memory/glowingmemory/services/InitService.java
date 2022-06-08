package com.memory.glowingmemory.services;

import com.memory.glowingmemory.mapper.InitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author zc
 */
@Slf4j
@Service
public class InitService {

    @Autowired
    InitRepository initRepository;

    /*
        @PostContruct是Java自带的注解，在方法上加该注解会在spring容器初始化的时候执行该方法。

    */
    @PostConstruct
    public void init() {
        log.info("项目启动 init");
//        Boolean exists = existsByIdAndStatus(1, 1);
//        log.info("id:1, status:1 查询结果为: {}", exists);
    }

    public Boolean existsByIdAndStatus(int id, int status) {
        return initRepository.existsByIdAndStatus(id, status);
    }

}
