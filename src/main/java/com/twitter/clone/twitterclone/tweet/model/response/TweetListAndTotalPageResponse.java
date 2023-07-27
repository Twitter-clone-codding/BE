package com.twitter.clone.twitterclone.tweet.model.response;

import java.util.List;

public record TweetListAndTotalPageResponse<T>(
        List<T> tweetsList, // 트윗내용
        Integer totalPage // 토탈 페이지
) {

}
