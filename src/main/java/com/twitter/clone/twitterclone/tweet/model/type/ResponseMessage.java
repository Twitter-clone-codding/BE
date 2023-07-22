package com.twitter.clone.twitterclone.tweet.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    TWEET_DELETE("해당 트윗이 삭제 되었습니다."),
    TWEET_LIST("해당 트윗 리스트를 조회 하셨습니다.")
    ;

    private final String msg;

}
