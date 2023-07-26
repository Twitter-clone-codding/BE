package com.twitter.clone.twitterclone.global.execption.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RegisterErrorCode {

    SAME_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일이 있습니다."),
    NO_SUCCESS_KEY(HttpStatus.BAD_REQUEST, "인증번호가 맞지 않습니다."),
    EMPTY_SUCCESS_KEY(HttpStatus.BAD_REQUEST, "인증번호가 없습니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
