package com.twitter.clone.twitterclone.search.model.response;

import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record SearchTweetsResponse(
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
