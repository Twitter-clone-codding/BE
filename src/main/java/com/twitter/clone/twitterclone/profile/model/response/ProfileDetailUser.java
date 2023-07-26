package com.twitter.clone.twitterclone.profile.model.response;

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
