package com.chandankrr.userservice.service;

import com.chandankrr.userservice.dto.UserInfoDto;
import com.chandankrr.userservice.entity.UserInfo;
import com.chandankrr.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto) {
        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .map(existingUser -> updateExistingUser(existingUser, userInfoDto))
                .orElseGet(() -> createNewUser(userInfoDto));

        return userInfoToDto(userInfo);
    }

    private UserInfo updateExistingUser(UserInfo userInfo, UserInfoDto userInfoDto) {
        userInfo.setUserId(userInfoDto.getUserId());
        userInfo.setFirstName(userInfoDto.getFirstName());
        userInfo.setLastName(userInfoDto.getLastName());
        userInfo.setEmail(userInfoDto.getEmail());
        userInfo.setPhoneNumber(userInfoDto.getPhoneNumber());
        userInfo.setProfilePic(userInfoDto.getProfilePic());
        return userRepository.save(userInfo);
    }

    private UserInfo createNewUser(UserInfoDto userInfoDto) {
        UserInfo userInfo = dtoToUserInfo(userInfoDto);
        return userRepository.save(userInfo);
    }

    public UserInfoDto getUserByUserId(String userId) throws Exception {
        Optional<UserInfo> userInfoOpt = userRepository.findByUserId(userId);
        if(userInfoOpt.isEmpty()){
            throw new Exception("User not found");
        }

        UserInfo userInfo = userInfoOpt.get();
        return userInfoToDto(userInfo);
    }

    private UserInfo dtoToUserInfo(UserInfoDto userInfoDto) {
        return UserInfo.builder()
                .userId(userInfoDto.getUserId())
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .profilePic(userInfoDto.getProfilePic())
                .build();
    }

    private UserInfoDto userInfoToDto(UserInfo userInfo) {
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .profilePic(userInfo.getProfilePic())
                .build();
    }

}
