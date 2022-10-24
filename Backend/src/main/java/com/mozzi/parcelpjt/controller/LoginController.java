package com.mozzi.parcelpjt.controller;

import com.mozzi.parcelpjt.dto.Member;
import com.mozzi.parcelpjt.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/user")
    public Long login(@RequestParam String deviceUUID, @RequestParam String deviceModel, HttpServletRequest request){
        return memberService.joinMember(deviceUUID, deviceModel, request);
    }

    @GetMapping("/user")
    public Member login(@RequestParam String deviceUUID) {
        return memberService.selectMember(deviceUUID);
    }
}
