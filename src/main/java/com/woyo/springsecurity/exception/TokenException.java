package com.woyo.springsecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenException extends ResponseStatusException {
    private static final String MESSAGE = "Authentication Failure, Invalid Token";
    public TokenException() {
        super(HttpStatus.UNAUTHORIZED, MESSAGE);
    }
}
