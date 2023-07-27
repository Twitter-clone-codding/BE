package com.twitter.clone.twitterclone.profile.model.response;

import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record UserTweetsResponse (
        Long id,
        TweetUserResponse user,
        String content,
        String hashtag,
        Integer hearts,
        boolean heartsCheck,
        Integer views,
        //이미지
        List<String> imgList,
        LocalDateTime createdAt
)
{
}
