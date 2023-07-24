package com.twitter.clone.twitterclone;

import com.twitter.clone.twitterclone.authlogin.model.entity.User;
import com.twitter.clone.twitterclone.authlogin.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class TwitterCloneApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;
	@Test
	@DisplayName("로그인 유저 테스트")
	@Rollback(value = false)
	void test1() {
		User user = new User();
		user.setId(1L);
		user.setEmail("aaa@gmail.com");
		String pw =  passwordEncoder.encode("12345678");
		user.setPassword(pw);

		userRepository.save(user);
		System.out.println("user.getEmail() = " + user.getEmail());
		System.out.println("user.getPassword() = " + user.getPassword());
	}

}
