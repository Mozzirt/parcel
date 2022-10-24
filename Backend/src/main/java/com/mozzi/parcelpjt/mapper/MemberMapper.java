package com.mozzi.parcelpjt.mapper;

import com.mozzi.parcelpjt.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    // 회원가입
    Long joinMember(String deviceUUID, String deviceModel, String browser, String ip);

    // 회원조회
    Member selectMember(String deviceUUID);

}
