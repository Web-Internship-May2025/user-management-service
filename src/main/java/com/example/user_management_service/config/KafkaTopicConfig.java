package com.example.user_management_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    NewTopic carRequest() {
        return TopicBuilder.name("policy.subscriber.request")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean NewTopic carResponse() {
        return TopicBuilder.name("policy.subscriber.response")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
