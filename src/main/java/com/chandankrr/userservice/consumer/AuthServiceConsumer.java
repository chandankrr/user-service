package com.chandankrr.userservice.consumer;

import com.chandankrr.userservice.dto.UserInfoDto;
import com.chandankrr.userservice.service.RedisService;
import com.chandankrr.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {

    private final UserService userService;
    private final RedisService redisService;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceConsumer.class);

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDto eventData) {
        logger.info("Received event data: {}", eventData);
        try {
            if (isInvalid(eventData)) {
                logger.error("Invalid event data: {}", eventData);
                return;
            }

            String key = "user:" + eventData.getUserId();
            if (redisService.isDuplicate(key)) {
                logger.warn("Duplicate event data: {}", eventData);
                return;
            }

            redisService.storeKey(key, Duration.ofHours(1));
            userService.createOrUpdateUser(eventData);
            logger.info("Saved event data: {}", eventData);
        } catch (Exception e) {
            logger.error("AuthServiceConsumer: An error occurred while consuming kafka event: ", e);
        }
    }

    private boolean isInvalid(UserInfoDto eventData) {
        return !Pattern.matches(EMAIL_REGEX, eventData.getEmail());
    }
}
