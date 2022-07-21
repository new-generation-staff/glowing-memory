package com.memory.glowingmemory.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author zc
 */
@Component
@Slf4j
public class TestJob {
    @Scheduled(cron = "0 0/5 * * * ?")
    public void testJob() {
        log.info("test job====");
    }
}
