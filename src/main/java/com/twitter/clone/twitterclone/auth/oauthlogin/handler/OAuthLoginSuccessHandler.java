package com.twitter.clone.twitterclone.auth.oauthlogin.handler;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.auth.oauthlogin.service.UserService;
import com.twitter.clone.twitterclone.global.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	// UserService를 주입받아 사용자 정보를 DB에서 조회하는데 사용합니다.
	private final UserService userService;
	private final JwtUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {

		// 인증 성공 후 OAuth2AuthenticationToken 객체를 얻습니다.
		// 이 객체에는 사용자가 OAuth2를 통해 인증하는 동안 얻은 정보가 포함되어 있습니다.
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

		// OAuth2 인증을 통해 얻은 사용자의 이메일 주소를 추출합니다.
		String email = token.getPrincipal().getAttribute("email").toString();

		// 로그에 사용자 이메일 기록 (인증 성공)
		log.info("LOGIN SUCCESS : {}", email);

		// UserService를 사용하여 데이터베이스에서 해당 이메일의 사용자 정보를 조회합니다.
		User user = userService.selectUser(email);

//		// ObjectMapper를 사용하여 사용자 객체를 JSON 문자열로 변환합니다.
//		String messageValue = new ObjectMapper().writeValueAsString(user);
//
//		// 응답 본문에 JSON 문자열을 작성하고 콘텐츠 유형을 설정합니다.
//		response.setContentType("application/json");
//		response.setCharacterEncoding("utf-8");
//		response.getWriter().write(messageValue);

		// Create a new cookie with the JWT token
		String newToken = jwtUtil.createToken(user.getEmail());
		// URL-safe encode the JWT token
		String encodedToken = URLEncoder.encode(newToken, StandardCharsets.UTF_8.toString());
		response.setHeader(JwtUtil.AUTHORIZATION_HEADER, encodedToken);

		Cookie cookie = new Cookie("token", encodedToken);

		// Set the cookie's path and max age (optional)
		cookie.setPath("/");
		cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days

		// Add the cookie to the response
		response.addCookie(cookie);
//		UserDto userDto = new UserDto(user.getEmail(), user.getNickname(), user.getProfileImageUrl());
//
//		String userJson = new ObjectMapper().writeValueAsString(userDto);
//
//		// 응답 본문에 JSON 문자열을 작성하고 콘텐츠 유형을 설정합니다.
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
//		ServletOutputStream out = response.getOutputStream();
//		out.print(userJson);
//		out.flush();

		// 추가적인 인증 성공 처리를 위해 부모 클래스의 onAuthenticationSuccess 메소드를 호출합니다.
		// 일반적으로 이 메소드는 사용자를 기본 페이지 또는 지정된 페이지로 리다이렉션합니다.
		super.onAuthenticationSuccess(request, response, authentication);
	}
}