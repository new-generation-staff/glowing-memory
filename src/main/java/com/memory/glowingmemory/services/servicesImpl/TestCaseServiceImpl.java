package com.memory.glowingmemory.services.servicesImpl;

import com.memory.glowingmemory.services.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zc
 */
@Slf4j
@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Async
    @Override
    public void async1() {
        log.info("async1:{}", Thread.currentThread().getName());
    }

    @Async
    @Override
    public void async2() {
        log.info("async2:{}", Thread.currentThread().getName());
    }
}
