package com.mozzi.parcelpjt.controller.exception;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private int statusCode;

    private String resultCode;

    private String error;

    private String detailMessage;

}
