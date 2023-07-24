package com.twitter.clone.twitterclone.register.controller;

import com.twitter.clone.twitterclone.register.model.request.RegisterRequest;
import com.twitter.clone.twitterclone.register.model.type.StatusCode;
import com.twitter.clone.twitterclone.register.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public StatusCode register(@RequestBody RegisterRequest request) {
        registerService.register(request);

        return new StatusCode(HttpStatus.OK.value(),"회원가입에 성공하였습니다.");
    }

}