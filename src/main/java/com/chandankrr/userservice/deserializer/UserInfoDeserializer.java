package com.chandankrr.userservice.deserializer;

import com.chandankrr.userservice.dto.UserInfoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {

    private final ObjectMapper objectMapper;

    public UserInfoDeserializer() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public UserInfoDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, UserInfoDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize UserInfoDto", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {}
}
