package com.mozzi.parcelpjt.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.mozzi.parcelpjt.config.response.CommonConstants.SESSION_NAME;


@Slf4j
public class DeviceCheckFilter implements Filter {

    private static final String[] whitelist = {"/*"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        try{
            if(checkPath(requestURI)){
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SESSION_NAME) == null){
                    // 디바이스 정보를 받을 수 없을시 팅구기
                    httpResponse.sendRedirect("/?continue=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request, response);
        }catch(Exception e){
            log.error("[Device Check Filter] = {}", e);
            throw e;
        }
    }

    private boolean checkPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist,requestURI);
    }

}
