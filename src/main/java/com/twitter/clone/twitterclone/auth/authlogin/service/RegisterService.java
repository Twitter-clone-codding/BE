package com.twitter.clone.twitterclone.auth.authlogin.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.auth.authlogin.model.request.RegisterRequest;
import com.twitter.clone.twitterclone.auth.common.repository.UserRepository;
import com.twitter.clone.twitterclone.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    public void register(RegisterRequest request) {
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();
        String birthday = request.getBirthday();

        // 회원 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        String[] tag = request.getEmail().split("@");
        String tagName = tag[0];
        Optional<User> checkTagName = userRepository.findByTagName(tagName);
        if (checkTagName.isPresent()) {
            int suffix = 1;
            while (checkTagName.isPresent()) {
                tagName = tag[0] + suffix;
                checkTagName = userRepository.findByTagName(tagName);
                suffix++;
            }
        }


        // 사용자 등록
        User user = new User(email, password, nickname, birthday, tagName);
        userRepository.save(user);
        //redis 에 있는 인증코드 삭제.
        redisUtil.setString("email : "+request.getEmail(), "", 1, TimeUnit.MILLISECONDS);

    }

}