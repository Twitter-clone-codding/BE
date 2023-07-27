package com.twitter.clone.twitterclone.tweet.model.request;

public record TweetsRequest(
        String content, // 트윗내용
        String hashtag // 해시태그
) {
}
