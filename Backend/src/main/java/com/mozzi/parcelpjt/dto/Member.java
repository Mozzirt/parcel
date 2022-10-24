package com.mozzi.parcelpjt.dto;

import lombok.Data;

@Data
public class Member {

    private String deviceUuid; // 디바이스 고유번호
    private String deviceModel; // 디바이스 종류
    private String browser;
    private String ip;
    private String snsConnect; // sns 연동정보
    private String createDate;

}
