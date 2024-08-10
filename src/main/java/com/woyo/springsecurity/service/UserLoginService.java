package com.woyo.springsecurity.service;

import com.woyo.springsecurity.dto.request.UserLoginRequest;
import com.woyo.springsecurity.dto.response.UserLoginResponse;
import com.woyo.springsecurity.entity.User;
import com.woyo.springsecurity.entity.projection.UserAuthoritiesProjection;
import com.woyo.springsecurity.exception.CustomException;
import com.woyo.springsecurity.repository.UserRepository;
import com.woyo.springsecurity.repository.UserRoleRepository;
import com.woyo.springsecurity.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;
    private final JwtUtil jwtUtil;

    public UserLoginResponse userLogin(UserLoginRequest request) {
        User user = validateUserAccount(request);
        List<String> userAuthorities = fetchUserAuthorities(user);
        String accessToken = generateAccessToken(user, userAuthorities);
        String refreshToken = generateRefreshToken(user);

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private List<String> fetchUserAuthorities(User user) {
        List<UserAuthoritiesProjection> userAuthorities = userRoleRepository.findUserAuthorities(user.getUserId());
        return userAuthorities.stream()
                .map(UserAuthoritiesProjection::getAuthoritiesName)
                .toList();
    }

    private User validateUserAccount(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(this::throwEmailPasswordWrong);

        boolean isCorrectPassword = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isCorrectPassword) throw throwEmailPasswordWrong();
        return user;
    }

    private String generateAccessToken(User user, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities);
        claims.put("userId", user.getUserId());
        claims.put("ipAddress", httpServletRequest.getRemoteAddr());
        return jwtUtil.generateToken(claims);
    }

    private String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        return jwtUtil.generateToken(claims);
    }

    private CustomException throwEmailPasswordWrong() {
       return new CustomException("5000", "Email/password is wrong");
    }
}
