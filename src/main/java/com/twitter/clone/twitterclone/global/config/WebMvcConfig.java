package com.twitter.clone.twitterclone.global.config;

import com.sun.mail.imap.protocol.UIDSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:3000",
            "http://172.20.10.10:3000");

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(ALLOWED_ORIGINS.toArray(new String[0])) // 도메인
//                .allowedOrigins(ALLOWED_ORIGINS[0]) // 도메인
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3000);
    }

}
