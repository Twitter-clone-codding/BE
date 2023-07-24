package com.twitter.clone.twitterclone.tweet.model.request;

public record TweetsRequest(
        String content,
        String hashtag
) {
}
