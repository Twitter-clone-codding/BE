package com.twitter.clone.twitterclone.auth.service;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.auth.model.request.RegisterRequest;
import com.twitter.clone.twitterclone.auth.repository.UserRepository;
import com.twitter.clone.twitterclone.global.execption.RegisterExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.type.RegisterErrorCode;
import com.twitter.clone.twitterclone.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

        if(Objects.isNull(request.getSuccessKey())){
            throw new RegisterExceptionImpl(RegisterErrorCode.EMPTY_SUCCESS_KEY);
        }

        if(!(request.getSuccessKey().equals(redisUtil.getString("email : "+request.getEmail())))){
            throw new RegisterExceptionImpl(RegisterErrorCode.NO_SUCCESS_KEY);
        }

        // 회원 중복 확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new RegisterExceptionImpl(RegisterErrorCode.SAME_EMAIL);
        }

        // 태그네임 생성
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