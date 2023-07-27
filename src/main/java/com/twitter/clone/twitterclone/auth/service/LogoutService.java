package com.twitter.clone.twitterclone.auth.service;

import com.twitter.clone.twitterclone.global.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // Cookie 삭제
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, null); // Name-Value
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // SameSite=None 설정을 사용하려면 Secure도 true로 설정해야함.
        cookie.setAttribute("SameSite", "None");
        cookie.setMaxAge(0); // 쿠키 유효시간 설정

        // Response 객체에 Cookie 추가
        response.addCookie(cookie);
    }
}
