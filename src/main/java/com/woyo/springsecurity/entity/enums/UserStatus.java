package com.woyo.springsecurity.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    PENDING_APPROVAL,
    APPROVED;
}
