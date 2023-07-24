package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tweets/heart")
public class TweetHeartController {

    private final TweetHeartService heartService;

    @GetMapping("/{tweetId}")
    public CustomResponse<?> likeTweet(
            @PathVariable Long tweetId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        String msg = heartService.checkTweetLike(tweetId, userDetails) ? ResponseMessage.TWEET_LIKE.getMsg() : ResponseMessage.TWEET_LIKE_CANCEL.getMsg();
        return CustomResponse.success(msg, null);
    }

}
