package com.mozzi.parcelpjt.controller.exception.custom;

import org.aspectj.bridge.IMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such tracking number")
public class NoSuchTrackingNumberException extends RuntimeException {
    public NoSuchTrackingNumberException(String message) {
        super(message);
    }

}

