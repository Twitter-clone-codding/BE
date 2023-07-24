package com.twitter.clone.twitterclone.following.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FollowingResponseMessage {

    FOLLOWING_USER("해당 유저를 팔로잉 하셨습니다."),
    FOLLOWING_USER_CANCEL("해당 유저를 팔로잉 취소 하셨습니다.")

    ;

    private final String msg;

}
