package com.twitter.clone.twitterclone.tweet.model.response;

import java.util.List;

public record TweetsListResponse(
        Long id,
        TweetUserResponse user,
        String content,
        String hashtag,
        Integer hearts,
        Integer views,
        List<String> imgList
) {
}
