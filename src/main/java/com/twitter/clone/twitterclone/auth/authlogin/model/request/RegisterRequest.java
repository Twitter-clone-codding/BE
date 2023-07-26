package com.twitter.clone.twitterclone.auth.authlogin.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Email(message = "잘못된 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "잘못된 비밀번호 형식입니다.", regexp = "(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,15}")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(message = "닉네임은 15자 미만입니다.", max = 15)
    private String nickname;

    @NotBlank(message = "생일을 입력해주세요.")
    private String birthday;

    private String successKey;
}