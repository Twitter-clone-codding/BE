package com.twitter.clone.twitterclone.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.clone.twitterclone.auth.authlogin.model.request.LoginRequestDto;
import com.twitter.clone.twitterclone.auth.common.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JwtUtil jwtUtil;


    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공");
        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();

        // token 값
        String result = jwtUtil.createToken(email);
        jwtUtil.addJwtToCookie(result, response);

        writeJsonResponse(request , response, result, ResponseMessage.LOGIN_SUCCESS.getMsg());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.info("로그인 실패");
        writeJsonResponse(request , response, null, ResponseMessage.LOGIN_FAIL.getMsg());

    }

    // JSON 형식의 응답을 클라이언트로 보내는 메서드
    public void writeJsonResponse(HttpServletRequest request, HttpServletResponse response, String result, String msg ) throws IOException {

        // HTTP 응답을 설정하는 코드
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // json 형태로 보낼 문자열
        String json = "{\"msg\": \"" +msg+ "\",\n\t\"result\": \"" + result + "\"}";

        // json 문자열을 클라이언트로 출력
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }

}