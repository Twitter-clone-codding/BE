package com.twitter.clone.twitterclone.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class CustomOAuthUserService extends DefaultOAuth2UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// OAuth2User 정보를 불러오는 메소드

		// 유저의 email과 oauthType을 호출
		Map<String, Object> attributes = super.loadUser(userRequest).getAttributes(); // OAuth2User의 속성들을 맵 형태로 가져옵니다.
		log.info("ATTR INFO : {}", attributes.toString()); // 속성들을 로그에 출력합니다.

		String googleId = attributes.get("sub").toString(); // 구글 고유 아이디
		String email = attributes.get("email").toString();

		userRepository.findByEmail(email).ifPresent(user -> {
					throw new RuntimeException("이미 회원가입된 이메일 입니다");});

		var user = userRepository.findByGoogleId(googleId)
				.orElseGet(() ->{
					String password = UUID.randomUUID().toString();
					String encodedPassword = passwordEncoder.encode(password);
					String name = attributes.get("name").toString(); // 이름
					String tag_id = System.nanoTime() + name;
					String profileImage = attributes.get("picture").toString();
					return new UserDomain(name, email, encodedPassword, tag_id, profileImage, googleId);
				});
		userRepository.save(user);

		return super.loadUser(userRequest); // OAuth2User 정보 반환
	}
};
