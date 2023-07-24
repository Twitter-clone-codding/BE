package com.twitter.clone.twitterclone.auth.common.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    LOGIN_SUCCESS("로그인이 성공되었습니다."),
    LOGIN_FAIL("로그인 실패");

    private final String msg;
}
