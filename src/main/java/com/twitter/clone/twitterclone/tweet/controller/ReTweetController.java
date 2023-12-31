package com.twitter.clone.twitterclone.tweet.controller;


import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.tweet.model.response.ReTweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.ReTweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retweets")
@RequiredArgsConstructor
public class ReTweetController {

    private final ReTweetService retweetService;

    @GetMapping("/{MainTweetId}")
    public CustomResponse<?> getListReTweet(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @PathVariable Long MainTweetId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        TweetListAndTotalPageResponse tweetListAndTotalPageResponses = retweetService.retweetPostList(page, limit, MainTweetId,userDetails);

        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), tweetListAndTotalPageResponses);
    }
}
