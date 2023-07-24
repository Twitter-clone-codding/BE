package com.twitter.clone.twitterclone.tweet.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record TweetsResponse(
        //유저 정보
//        Long id,
        TweetUserResponse user,
        String content,
        String hashtag,
        Integer hearts,
        Integer views,
        //이미지
        List<String> imgList,
        LocalDateTime createdAt
) {

}
