package com.twitter.clone.twitterclone.auth.oauthlogin.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.auth.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

	private final UserRepository userRepository;

	public User selectUser(String email) {
		return userRepository.findByEmail(email).orElseThrow(null);
	}
}