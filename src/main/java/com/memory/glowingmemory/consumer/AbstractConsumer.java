package com.memory.glowingmemory.consumer;

import com.memory.glowingmemory.config.RedisKeys;
import com.memory.glowingmemory.util.HashUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author zc
 */
@Slf4j
public abstract class AbstractConsumer {
    @Autowired
    StringRedisTemplate redisTemplate;

    protected boolean checkIdempotenceById(String consumer, String id) {
        String key = String.format(RedisKeys.IDEMPOTENT, consumer, id);
        String value = Instant.now().toString();

        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(key, value, Duration.of(12, ChronoUnit.HOURS));

        boolean firstTime = result != null && result;

        if (!firstTime) {
            log.warn("Duplicated kafka message, consumer={}, id={}", consumer, id);
        }

        return firstTime;
    }

    protected boolean checkIdempotenceByConsumerRecord(String consumer, ConsumerRecord<String, String> consumerRecord) {
        String md5 = HashUtils.md5(consumerRecord.value());

        return checkIdempotenceById(consumer, md5);
    }
}
