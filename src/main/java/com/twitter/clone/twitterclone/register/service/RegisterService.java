package com.twitter.clone.twitterclone.register.service;

import com.twitter.clone.twitterclone.register.model.entity.User;
import com.twitter.clone.twitterclone.register.model.request.RegisterRequest;
import com.twitter.clone.twitterclone.register.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();
        String birthday = request.getBirthday();

        // 회원 중복 확인
        Optional<User> checkEmail = registerRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(email, password, nickname, birthday);
        registerRepository.save(user);
    }

}