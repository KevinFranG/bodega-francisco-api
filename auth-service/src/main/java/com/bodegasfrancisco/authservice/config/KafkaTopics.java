package com.bodegasfrancisco.authservice.config;

import com.bodegasfrancisco.kafka.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    @Bean
    public NewTopic customerRegistrationRequested() {
        return TopicBuilder
            .name(Topics.CUSTOMER_CREATION_REQUESTED)
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic customerCreation() {
        return TopicBuilder
            .name(Topics.CUSTOMER_CREATED)
            .partitions(1)
            .replicas(1)
            .build();
    }
}
