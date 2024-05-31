package com.chandankrr.userservice.consumer;

import com.chandankrr.userservice.dto.UserInfoDto;
import com.chandankrr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceConsumer.class);

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData) {
        logger.info("Received event data: {}", eventData);
        try {
            // TODO: Make it transactional to handle idempotency and validate email, phoneNumber etc
            userService.createOrUpdateUser(eventData);
        } catch (Exception e) {
            logger.error("AuthServiceConsumer: An error occurred while consuming kafka event: ", e);
        }
    }
}
