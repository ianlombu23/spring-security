package com.woyo.springsecurity.controller;

import com.woyo.springsecurity.dto.request.UserLoginRequest;
import com.woyo.springsecurity.dto.request.UserRegisterRequest;
import com.woyo.springsecurity.dto.response.UserDetailResponse;
import com.woyo.springsecurity.dto.response.UserLoginResponse;
import com.woyo.springsecurity.dto.response.UserRegisterResponse;
import com.woyo.springsecurity.service.UserDetailService;
import com.woyo.springsecurity.service.UserLoginService;
import com.woyo.springsecurity.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final UserDetailService userDetailService;

    @PostMapping("/create")
    public UserRegisterResponse userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userRegisterService.createUser(userRegisterRequest);
    }

    @PostMapping("/login")
    public UserLoginResponse userLogin(@RequestBody UserLoginRequest userLoginRequest) {
        return userLoginService.userLogin(userLoginRequest);
    }

    @GetMapping("/detail")
    public UserDetailResponse userDetail() {
        return userDetailService.userDetail();
    }

    @GetMapping("/private")
    @PreAuthorize("hasAnyAuthority('MENU_REPORTING')")
    public String webPrivate() {
        return "Menu Reporting Access";
    }
}
