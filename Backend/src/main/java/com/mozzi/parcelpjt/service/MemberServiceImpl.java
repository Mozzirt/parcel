package com.mozzi.parcelpjt.service;

import com.mozzi.parcelpjt.dto.Member;
import com.mozzi.parcelpjt.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Override
    public Long joinMember(String deviceUUID, String deviceModel) {
        return memberMapper.joinMember(deviceUUID, deviceModel);
    }

    @Override
    public Member selectMember(String deviceUUID) {
        return memberMapper.selectMember(deviceUUID);
    }
}
