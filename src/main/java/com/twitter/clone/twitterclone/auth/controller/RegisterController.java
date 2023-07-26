package com.twitter.clone.twitterclone.auth.controller;

import com.twitter.clone.twitterclone.auth.model.request.RegisterRequest;
import com.twitter.clone.twitterclone.auth.service.RegisterService;
import com.twitter.clone.twitterclone.auth.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public CustomResponse<String> register(@RequestBody @Valid RegisterRequest request) {
        registerService.register(request);

        return CustomResponse.success(ResponseMessage.REGISTER_SUCCESS.getMsg(), null);
    }

}
