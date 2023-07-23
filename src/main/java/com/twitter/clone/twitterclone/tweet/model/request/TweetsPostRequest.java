package com.twitter.clone.twitterclone.tweet.model.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record TweetsPostRequest(
        Long mainTweetId,       // 부모아이디
        TweetsRequest tweet  // 트윗내용
) {
}

