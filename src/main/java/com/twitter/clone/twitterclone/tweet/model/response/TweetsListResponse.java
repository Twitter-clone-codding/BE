package com.twitter.clone.twitterclone.tweet.model.response;

import java.util.List;

public record TweetsListResponse(
        //TODO: 유저 정보도 포함 해야함. 로그인 기능 완료시 추가 예정
        String content,
        String hashtag,
        Integer hearts,
        Integer views,
        List<String> imgList
) {
}
