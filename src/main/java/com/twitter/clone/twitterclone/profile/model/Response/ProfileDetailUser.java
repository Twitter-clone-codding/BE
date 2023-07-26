package com.twitter.clone.twitterclone.profile.model.Response;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;

import java.time.LocalDateTime;
import java.util.List;

public record ProfileDetailUser (
        String nickname,
        String tagName,
        String profileImageUrl,
        String profileBackgroundImageUrl,
        String content,
        String url,
        LocalDateTime createdAt,
        List<UserTweetsResponse> myList
){

}
