package com.mozzi.parcelpjt.controller.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Device already exists")
public class DuplicateDeviceException extends RuntimeException {
    public DuplicateDeviceException(String message) {
        super(message);
    }
}