package com.twitter.clone.twitterclone.search.model.response;

import java.util.List;

public record SearchTweetListAndTotalPageResponse(
        List<SearchTweetsResponse> tweetsList,
        Integer totalPage
) {

}
