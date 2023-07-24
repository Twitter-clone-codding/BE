package com.twitter.clone.twitterclone.auth.common.config;

import com.twitter.clone.twitterclone.auth.oauthlogin.handler.OAuthLoginFailureHandler;
import com.twitter.clone.twitterclone.auth.oauthlogin.handler.OAuthLoginSuccessHandler;
import com.twitter.clone.twitterclone.auth.oauthlogin.service.CustomOAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { // SecurityConfig 클래스 선언

	private final CustomOAuthUserService userService;
	private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
	private final OAuthLoginFailureHandler oAuthLoginFailureHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // SecurityFilterChain Bean을 생성하는 메소드 선언
		http
				.csrf(csrf -> csrf.disable()) // CSRF (Cross-Site Request Forgery) 보호를 비활성화
				.authorizeRequests(authorizeRequests -> authorizeRequests
						.requestMatchers("/login/**").permitAll() // "/login/**" 경로에 대한 요청은 모두 허용
						.anyRequest().authenticated()) // 그 외 모든 요청은 인증된 사용자만 허용
				.oauth2Login(oauth2Login -> oauth2Login
						.loginPage("/login") // OAuth2 로그인 페이지를 "/login"으로 설정
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
								.userService(userService)) // OAuth2 로그인 후 사용자 정보를 가져오는 데 사용할 서비스를 userService로 설정
						.successHandler(oAuthLoginSuccessHandler) // OAuth2 로그인 성공 시 실행할 핸들러 설정
						.failureHandler(oAuthLoginFailureHandler)); // OAuth2 로그인 실패 시 실행할 핸들러 설정

		return http.build(); // HttpSecurity 객체를 빌드하고 반환
	}
}
