package com.twitter.clone.twitterclone.tweet.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    TWEET_POST("트윗이 등록 되었습니다."),
    TWEET_DETAIL("해당 트윗을 조회 하셨습니다."),
    TWEET_DELETE("해당 트윗이 삭제 되었습니다."),
    TWEET_LIST("해당 트윗 리스트를 조회 하셨습니다."),
    TWEET_LIKE_CANCEL("해당 트윗를 좋아요 취소하셨습니다."),
    TWEET_LIKE("해당 트윗을 좋아요를 누르셨습니다."),
    TWEET_HASHTAG_RANK("성공적으로 HashTag 랭킹 리스트를 조회하셨습니다.")

    ;

    private final String msg;

}
