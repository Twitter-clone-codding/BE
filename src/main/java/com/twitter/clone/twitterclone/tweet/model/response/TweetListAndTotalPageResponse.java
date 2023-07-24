package com.twitter.clone.twitterclone.tweet.model.response;

import java.util.List;

public record TweetListAndTotalPageResponse<T>(
        List<T> tweetsList,
        Integer totalPage
) {

}
