package com.twitter.clone.twitterclone.auth.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    LOGIN_SUCCESS("로그인이 성공되었습니다."),
    LOGIN_FAIL("로그인 실패"),
    REGISTER_SUCCESS("회원가입에 성공되었습니다."),
    SENDEMAIL_SUCCESS("이메일 인증코드를 보냈습니다."),
    VERIFYEMAILCODE_SUCCESS("이메일 인증코드가 확인되었습니다."),
    VERIFYEMAILCODE_FAIL("이메일 인증코드가 일치하지 않습니다.");

    private final String msg;
}
