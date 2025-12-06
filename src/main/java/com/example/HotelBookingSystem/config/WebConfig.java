package com.example.HotelBookingSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/dashboard/**") // 🔒 Bảo vệ toàn bộ admin
                .excludePathPatterns(
                        "/",                     // public
                        "/searchRoom",           // public
                        "/detail/**",            // public
                        "/detailbooking/**",     // public
                        "/booking_user",         // public
                        "/login",                // public
                        "/logout",               // public
                        "/css/**", "/js/**", "/images/**", "/assets/**" // file tĩnh
                );
    }
}
