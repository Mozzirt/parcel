package com.mozzi.parcelpjt.service;

import com.mozzi.parcelpjt.config.response.CommonConstants;
import com.mozzi.parcelpjt.dto.Member;
import com.mozzi.parcelpjt.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.mozzi.parcelpjt.config.response.CommonConstants.*;
import static com.mozzi.parcelpjt.config.response.CommonConstants.IS_EDGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    @Override
    public Long joinMember(String deviceUUID, String deviceModel, HttpServletRequest request) {
        String ip = ipCheck(request);
        String browser = deviceCheck(request);
        Member member = this.selectMember(deviceUUID);
        // 디바이스 정보 존재시 로그인처리
        if (!ObjectUtils.isEmpty(member)) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSION_NAME, deviceUUID);
            return 9L;
        }
//        if(!ObjectUtils.isEmpty(member)) {
//            throw new DuplicateDeviceException(deviceUUID);
//        }
        return memberMapper.joinMember(deviceUUID, deviceModel, browser, ip);
    }

    @Override
    public Member selectMember(String deviceUUID) {
        return memberMapper.selectMember(deviceUUID);
    }

    private String ipCheck (HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (!StringUtils.hasText(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasText(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasText(ip)) {
            ip = request.getRemoteAddr();
        }
        if(ip == null){
            ip = "";
        }
        return ip;
    }
    private String deviceCheck (HttpServletRequest request) {
        String ua = request.getHeader(CommonConstants.USER_AGENT).toLowerCase();
        // 모바일 체크
        if(ua.matches(".*(iphone|ipad|ipod|macintosh).*")){
            return IS_IOS;
        } else if (ua.indexOf("blackberry") > -1) {
            return IS_BLACKBERRY;
        } else if (ua.indexOf("android") > -1){
            return IS_ANDROID;
        }

        // 브라우저
        if(ua.indexOf("whale/") > -1){
            return IS_WHALE + ua.split("whale/")[1].split(" ")[0];
        }else if(ua.indexOf("opera/") > -1 || ua.indexOf("opr/") > -1){ //오페라
            if(ua.indexOf("opera/") > -1) {
                return IS_OPERA + ua.split("opera/")[1].split(" ")[0];
            }else if(ua.indexOf("opr/") > -1) {
                return IS_OPERA + ua.split("opr/")[1].split(" ")[0];
            }
        }else if(ua.indexOf("firefox/") > -1){
            return IS_FIREFOX + ua.split("firefox/")[1].split(" ")[0];
        }else if(ua.indexOf("safari/") > -1 && ua.indexOf("chrome/") == -1 ){ //사파리
            return IS_SAFARI + ua.split("safari/")[1].split(" ")[0];
        }else if(ua.indexOf("chrome/") > -1){
            return IS_CHROME + ua.split("chrome/")[1].split(" ")[0];
        }
        // IE
        if(ua.indexOf("windows") > -1 || ua.indexOf("trident") > -1 || ua.indexOf("msie") > -1) { //IE
            if(ua.indexOf("trident/7") > -1) {
                return IS_IE11;
            }else if(ua.indexOf("trident/6") > -1) {
                return IS_IE10;
            }else if(ua.indexOf("trident/5") > -1) {
                return IS_IE9;
            }else if(ua.indexOf("trident/4") > -1) {
                return IS_IE8;
            }else if(ua.indexOf("edge/") > -1) {
                return IS_EDGE + ua.split("edge/")[1].split(" ")[0];
            }else if(ua.indexOf("edg/") > -1) {
                return IS_EDGE + ua.split("edg/")[1].split(" ")[0];
            }
        }
        return "";
    }
}
