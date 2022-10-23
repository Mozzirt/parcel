package com.mozzi.parcelpjt.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import springfox.documentation.spring.web.json.Json;

import java.net.http.HttpTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName : com.mozzi.parcelpjt.controller
 * fileName : ControllerAdvice
 * author : kimbeomchul
 * date : 2022/10/24
 * description :
 * ===========================================================
 * DATE    AUTHOR    NOTE
 * -----------------------------------------------------------
 * 2022/10/22 kimbeomchul 최초 생성
 */

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpTimeoutException.class)
    public ResponseEntity<Map<String, String>> RequestTimeoutException(HttpTimeoutException exception) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorResultBody("공통화처리1", String.valueOf(exception)));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> notSupportedException(HttpRequestMethodNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResultBody("공통화처리2", String.valueOf(exception)));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> httpClientException(HttpClientErrorException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResultBody("공통화처리3", String.valueOf(exception)));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, String>> httpServerException(HttpServerErrorException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResultBody("공통화처리4", String.valueOf(exception)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> allException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResultBody("공통화처리5", String.valueOf(exception)));
    }
    
    private Map<String, String> errorResultBody(String message, String detailMessage) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        error.put("detail", detailMessage);
        return error;
    }

}
