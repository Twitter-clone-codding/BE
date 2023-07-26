package com.twitter.clone.twitterclone.tweet.model.response;

import java.time.LocalDateTime;
import java.util.List;

public record TweetsListResponse(
        Long id,
        TweetUserResponse user,
        String content,
        String hashtag,
        Integer hearts,
        Boolean heartCheck,
        Integer views,
        List<String> imgList,
        LocalDateTime createdAt
) {
}
