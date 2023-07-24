package com.twitter.clone.twitterclone.auth.common.config;

import com.twitter.clone.twitterclone.auth.common.repository.UserRepository;
import com.twitter.clone.twitterclone.auth.oauthlogin.handler.OAuthLoginFailureHandler;
import com.twitter.clone.twitterclone.auth.oauthlogin.handler.OAuthLoginSuccessHandler;
import com.twitter.clone.twitterclone.auth.oauthlogin.service.CustomOAuthUserService;
import com.twitter.clone.twitterclone.auth.oauthlogin.service.UserService;
import com.twitter.clone.twitterclone.global.security.JwtAuthenticationFilter;
import com.twitter.clone.twitterclone.global.security.JwtAuthorizationFilter;
import com.twitter.clone.twitterclone.global.security.UserDetailsServiceImpl;
import com.twitter.clone.twitterclone.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    private final CustomOAuthUserService userService;
    private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    private final OAuthLoginFailureHandler oAuthLoginFailureHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth/login") // OAuth2 로그인 페이지를 "/login"으로 설정
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(userService)) // OAuth2 로그인 후 사용자 정보를 가져오는 데 사용할 서비스를 userService로 설정
                        .successHandler(oAuthLoginSuccessHandler) // OAuth2 로그인 성공 시 실행할 핸들러 설정
                        .failureHandler(oAuthLoginFailureHandler)); // OAuth2 로그인 실패 시 실행할 핸들러 설정

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/oauth/loing").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/api/tweets/posts/**").permitAll()
                        .requestMatchers("/api/tweets/{MainTweetid}/**").permitAll()
                        .requestMatchers("/api/retweets/{MainTweetid}/**").permitAll()
                        .requestMatchers("/api/search/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                       // .requestMatchers("/").permitAll()  // 메인 페이지 요청 허가
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}