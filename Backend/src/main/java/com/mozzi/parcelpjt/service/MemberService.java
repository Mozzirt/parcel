package com.mozzi.parcelpjt.service;


import com.mozzi.parcelpjt.dto.Member;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {
    // 회원가입
    Long joinMember(String deviceUUID, String deviceModel, HttpServletRequest request);

    // 회원조회
    Member selectMember(String deviceUUID);

}
