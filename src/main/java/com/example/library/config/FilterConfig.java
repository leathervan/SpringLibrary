package com.example.library.config;

import com.example.library.filters.RoleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RoleFilter> roleFilterRegistration() {
        FilterRegistrationBean<RoleFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RoleFilter());
        registration.addUrlPatterns("/admin/*");
        registration.setOrder(2);
        return registration;
    }
}
