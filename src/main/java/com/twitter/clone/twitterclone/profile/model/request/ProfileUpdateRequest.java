package com.twitter.clone.twitterclone.profile.model.request;

public record ProfileUpdateRequest(
        String profileImageUrl,
        String nickname,
        String profileBackgroundUrl,
        String url,
        String content
) {

}

