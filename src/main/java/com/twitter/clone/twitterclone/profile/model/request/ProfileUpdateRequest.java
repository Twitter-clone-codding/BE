package com.twitter.clone.twitterclone.profile.model.request;

import org.springframework.web.multipart.MultipartFile;

public record ProfileUpdateRequest(
        MultipartFile profileImageUrl,
        String nickname,
        MultipartFile profileBackgroundUrl,
        String url,
        String content
) {

}

