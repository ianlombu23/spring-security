package com.woyo.springsecurity.service;

import com.woyo.springsecurity.dto.response.UserDetailResponse;
import com.woyo.springsecurity.entity.User;
import com.woyo.springsecurity.exception.CustomException;
import com.woyo.springsecurity.repository.UserRepository;
import com.woyo.springsecurity.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserRepository userRepository;

    public UserDetailResponse userDetail() {
        String userId = CommonUtil.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("4000", "User tidak ditemukan"));

        return UserDetailResponse.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .status(user.getStatus().name())
                .build();
    }
}
