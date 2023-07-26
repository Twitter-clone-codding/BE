package com.twitter.clone.twitterclone.global.execption.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode {

    NO_RECEIVER_USER(HttpStatus.BAD_REQUEST, "상대 유저가 존재 하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorMsg;
}
