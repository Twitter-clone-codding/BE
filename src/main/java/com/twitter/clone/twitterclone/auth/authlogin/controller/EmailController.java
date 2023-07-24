package com.twitter.clone.twitterclone.auth.authlogin.controller;

import com.twitter.clone.twitterclone.auth.authlogin.model.request.EmailCodeRequest;
import com.twitter.clone.twitterclone.auth.authlogin.model.request.EmailRequest;
import com.twitter.clone.twitterclone.auth.authlogin.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send/email")
    public String sendEmail(@RequestBody EmailRequest request) throws MessagingException, UnsupportedEncodingException {
        String emailCode = emailService.sendEmail(request.getEmail());
        return "인증번호 발송 " + emailCode;
    }

    @PostMapping("/verify/email")
    public String verifyEmailCode(@RequestBody EmailCodeRequest request) throws MessagingException, UnsupportedEncodingException {
//        emailService.verifyEmailCode(request);
        return "인증번호 확인 완료";
    }

}
