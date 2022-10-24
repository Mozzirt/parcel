package com.mozzi.parcelpjt.controller.exception;

import com.mozzi.parcelpjt.config.response.MessageConstants;
import com.mozzi.parcelpjt.controller.exception.custom.DuplicateDeviceException;
import com.mozzi.parcelpjt.controller.exception.custom.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.http.HttpTimeoutException;

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
    public ResponseEntity<ResponseMessage> requestTimeoutException(HttpTimeoutException exception) {
        log.warn("##### requestTimeoutException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.REQUEST_TIMEOUT.value())
                .resultCode(HttpStatus.REQUEST_TIMEOUT.getReasonPhrase())
                .error(MessageConstants.ER_00_0001)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseMessage> notSupportedException(HttpRequestMethodNotSupportedException exception) {
        log.warn("##### notSupportedException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .resultCode(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
                .error(MessageConstants.ER_00_0002)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ResponseMessage> httpClientException(HttpClientErrorException exception) {
        log.warn("##### httpClientException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .resultCode(HttpStatus.NOT_FOUND.getReasonPhrase())
                .error(MessageConstants.ER_00_0003)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ResponseMessage> httpServerException(HttpServerErrorException exception) {
        log.warn("##### httpServerException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .resultCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .error(MessageConstants.ER_00_0004)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicateDeviceException.class)
    public ResponseEntity<ResponseMessage> customDuplicateDeviceException(DuplicateDeviceException exception) {
        log.warn("##### customDuplicateDeviceException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .resultCode(HttpStatus.CONFLICT.getReasonPhrase())
                .error(MessageConstants.ER_00_0005)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseMessage> customUnauthorizedException(UnauthorizedException exception) {
        log.warn("##### customUnauthorizedException : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .resultCode(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .error(MessageConstants.ER_00_0006)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessage> allException(Exception exception) {
        log.warn("##### 최상위 Exception : {}", exception);
        return new ResponseEntity(ResponseMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .resultCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(MessageConstants.ER_00_0007)
                .detailMessage(exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
