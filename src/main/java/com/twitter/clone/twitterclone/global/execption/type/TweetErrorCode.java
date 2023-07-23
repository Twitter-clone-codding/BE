package com.twitter.clone.twitterclone.global.execption.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TweetErrorCode {

    NO_TWEET(HttpStatus.ACCEPTED, "트윗을 찾을수 없습니다.");


    private final HttpStatus httpStatus;
    private final String errorMsg;

}
