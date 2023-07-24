package com.twitter.clone.twitterclone.global.execption.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TweetErrorCode {

    NO_TWEET(HttpStatus.ACCEPTED, "트윗을 찾을수 없습니다."),
    NOT_MY_TWEET(HttpStatus.BAD_REQUEST, "해당 트윗은 본인것이 아닙니다."),
    EMPTY_CONTENT_AND_IMAGE(HttpStatus.NO_CONTENT,"내용 또는 이미지를 추가해주세요" );


    private final HttpStatus httpStatus;
    private final String errorMsg;

}
