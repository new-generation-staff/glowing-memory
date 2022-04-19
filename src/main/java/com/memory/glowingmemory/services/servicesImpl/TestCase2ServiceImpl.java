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
public class TestCase2ServiceImpl implements TestCaseService {

    @Async
    @Override
    public void async1() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("async1:{}", Thread.currentThread().getName());
    }

    @Async
    @Override
    public void async2() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("async2:{}", Thread.currentThread().getName());
    }
}
