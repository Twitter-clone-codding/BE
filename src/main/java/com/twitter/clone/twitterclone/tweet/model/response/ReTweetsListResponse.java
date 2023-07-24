package com.twitter.clone.twitterclone.tweet.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record ReTweetsListResponse(
//        Long id,
//        User user;
        String content,
        String hashtag,
        Integer hearts,
        Integer views,
        List<String> imgList,
        LocalDateTime createdAt
//        Long mainTweetId
) {
}
