package com.mozzi.parcelpjt.controller.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Data")
public class NoDataException extends RuntimeException {
    public NoDataException(String message) {
        super(message);
    }
}