package com.memory.glowingmemory.consumer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zc
 */
@Slf4j
@Component
public class TestConsumer extends AbstractConsumer {
    @KafkaListener(topics = "${kafka.testTopic.topic}",
            groupId = "${kafka.testTopic.groupId}",
            concurrency = "${kafka.testTopic.concurrency}",
            properties = {"max.poll.records=${kafka.testTopic.maxPollRecords}"}
    )
    //如果使用批量的话将参数改为   List<ConsumerRecord<String, String>>
    //如果使用 ConsumerRecord<String, String> 会根据 max.poll.records 循环多次
    public void onMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("{} kafka消息 received record: partition={}, leaderEpoch={}, offset={}, {}={}, key={}, headers={}, valueSize={}",
                consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.leaderEpoch(),
                consumerRecord.offset(),
                consumerRecord.timestampType(),
                consumerRecord.timestamp(),
                consumerRecord.key(),
                consumerRecord.headers(),
                consumerRecord.serializedValueSize());

        if (!checkIdempotenceByConsumerRecord(this.getClass().getName(), consumerRecord)) {
            return;
        }

        Map<String, Object> map = (Map<String, Object>) JSON.parse(consumerRecord.value());
        log.info("map:{}", map);
    }
}
