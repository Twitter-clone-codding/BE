package com.twitter.clone.twitterclone.tweet.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

public record TweetUserResponse(
        Long id,
        String nickname,
        String tagName,
        String profileImageUrl) {
}
