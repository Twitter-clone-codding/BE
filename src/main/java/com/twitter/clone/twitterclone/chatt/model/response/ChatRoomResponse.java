package com.twitter.clone.twitterclone.chatt.model.response;

public record ChatRoomResponse(

        Long id,
        String nickname,
        String tagName,
        String profileImageUrl,
        String roomKey
) {
}
