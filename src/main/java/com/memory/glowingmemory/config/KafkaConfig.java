package com.memory.glowingmemory.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * @author zc
 */
@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic testTopic(@Value("${kafka.testTopic.topic}") String topic,
                              @Value("${kafka.testTopic.partitions:10}") int partitions,
                              @Value("${kafka.testTopic.replicas:1}") int replicas) {
        return TopicBuilder.name(topic)
                .partitions(partitions)
                .replicas(replicas)
                .build();
    }

    //创建新的 kafkaFactory，适用于多套kafka集群
    /*@Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> cdpKafkaListenerContainerFactory(@Value("${cdp.kafka-bootstrap-servers}") String bootstrapServers) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        ConsumerFactory<String, String> cdpConsumerFactory = new DefaultKafkaConsumerFactory<>(props);

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cdpConsumerFactory);

        return factory;
    }*/
}
