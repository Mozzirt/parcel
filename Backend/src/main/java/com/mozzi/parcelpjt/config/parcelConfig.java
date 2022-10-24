package com.mozzi.parcelpjt.config;

import com.mozzi.parcelpjt.config.aop.TimeAop;
import com.mozzi.parcelpjt.config.filter.DeviceCheckFilter;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;

@Slf4j
@Configuration
public class parcelConfig {

    // TimeAop
    @Bean
    public TimeAop timeAop() {
        return new TimeAop();
    }

    // deviceCheck Filter
    @Bean
    public FilterRegistrationBean deviceCheckFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new DeviceCheckFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        return filterFilterRegistrationBean;
    }

    // XSS
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(2);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

}
