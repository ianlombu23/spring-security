package com.woyo.springsecurity.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public CommonUtil() {
    }

    public static String getUserId() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();
    }
}
