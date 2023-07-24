package com.twitter.clone.twitterclone.auth.authlogin.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
//    @NotBlank(groups = ValidationGroups.NotBlankGroup.class, message = "아이디를 입력해주세요.")
//    @Pattern(groups = ValidationGroups.PatternCheckGroup.class, message = "잘못된 이메일 형식입니다.", regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$")
    private String email;
//    @NotBlank(groups = ValidationGroups.NotBlankGroup.class, message = "비밀번호를 입력해주세요.")
//    @Pattern(groups = ValidationGroups.PatternCheckGroup.class, message = "잘못된 비밀번호 형식입니다.", regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,15}")
    private String password;
    private String nickname;
    private String birthday;
}