package com.twitter.clone.twitterclone.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService  {

	private final UserRepository userRepository;

	public UserDomain selectUser(String email) {
		return userRepository.findByEmail(email).orElseThrow(null);
	}
}