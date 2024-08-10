package com.woyo.springsecurity.service;

import com.woyo.springsecurity.dto.request.UserRegisterRequest;
import com.woyo.springsecurity.dto.response.UserRegisterResponse;
import com.woyo.springsecurity.entity.User;
import com.woyo.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterResponse createUser(UserRegisterRequest request) {
        String passwordEncode =  passwordEncoder.encode(request.getPassword());
        User userSave = userRepository.save(
                User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncode)
                        .build()
        );

        return UserRegisterResponse.builder()
                .userId(userSave.getUserId())
                .build();
    }
}
