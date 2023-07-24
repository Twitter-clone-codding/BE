package com.twitter.clone.twitterclone.auth.authlogin.service;

import com.twitter.clone.twitterclone.auth.authlogin.model.request.EmailCodeRequest;
import com.twitter.clone.twitterclone.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;

    private String emailCode;

    public void createEmailCode() {
        String randomStr = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            randomStr+=random.nextInt(9);
        }
        emailCode = randomStr;
    }

    public MimeMessage createEmail(String email) throws MessagingException, UnsupportedEncodingException {
        createEmailCode();

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject("twitter-clone 회원가입 인증 번호");
        message.setText("인증 번호 : " + emailCode, "utf-8", "html");
        message.setFrom(new InternetAddress("mailservicesoon@naver.com","twitter-clone_Admin"));

        return message;
    }

    public String sendEmail(String email) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmail(email);
        emailSender.send(emailForm);

        redisUtil.setString("email : "+email, emailCode, 30, TimeUnit.MINUTES);

        return emailCode;
    }

    public void verifyEmailCode(EmailCodeRequest request) {
        if (!(isVerify(request))) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
    }

    private boolean isVerify(EmailCodeRequest request) {
        return request.getEmail().equals(redisUtil.getString("email : "+request.getEmail()));
    }

}
