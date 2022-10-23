package com.mozzi.parcelpjt.config;

import com.mozzi.parcelpjt.config.aop.TimeAop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class parcelConfig {

    @Bean
    public TimeAop timeAop() {
        return new TimeAop();
    }
}
