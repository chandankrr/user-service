package com.chandankrr.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDto {

    @NonNull
    @JsonProperty("user_id")
    private String userId;

    @NonNull
    @JsonProperty("first_name")
    private String firstName;

    @NonNull
    @JsonProperty("last_name")
    private String lastName;

    @NonNull
    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private Long phoneNumber;

    @JsonProperty("profile_pic")
    private String profilePic;

}
