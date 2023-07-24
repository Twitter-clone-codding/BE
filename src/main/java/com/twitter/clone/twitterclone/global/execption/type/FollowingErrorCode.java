package com.twitter.clone.twitterclone.global.execption.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum FollowingErrorCode {

    NO_FOLLOWING(HttpStatus.BAD_REQUEST, "팔로워가 없습니다."),
    SELF_FOLLOWING_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "팔로잉을 자신한테 걸 수 없습니다."),
    NOT_FOLLOWING_USER(HttpStatus.BAD_REQUEST, "팔로잉할 유저가 없습니다.")
    ;

    private final HttpStatus status;
    private final String msg;
}
