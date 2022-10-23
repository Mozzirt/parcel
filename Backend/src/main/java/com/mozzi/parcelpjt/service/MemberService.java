package com.mozzi.parcelpjt.service;


import com.mozzi.parcelpjt.dto.Member;

public interface MemberService {
    // 회원가입
    Long joinMember(String deviceUUID, String deviceModel);

    // 회원조회
    Member selectMember(String deviceUUID);

}
