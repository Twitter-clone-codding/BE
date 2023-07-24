package com.twitter.clone.twitterclone.tweet.model.response;

import java.util.List;

public record TweetsListResponse(
        TweetUserResponse user,
        String content,
        String hashtag,
        Integer hearts,
        Integer views,
        List<String> imgList
) {
}
