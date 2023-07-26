package com.twitter.clone.twitterclone.chatt.model.message;

public record ChatMessage(
        String roomKey,
        String nickname,
        String msg
) {
}
