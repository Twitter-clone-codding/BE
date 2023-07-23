package com.twitter.clone.twitterclone.tweet.controller;


import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.tweet.model.response.ReTweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.ReTweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/retweets")
@RequiredArgsConstructor
public class ReTweetController {

    private final ReTweetService retweetService;

    @GetMapping("/{MainTweeid}")
    public CustomResponse<?> getListReTweet(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @PathVariable Long MainTweeid
    ) {

        List<ReTweetsListResponse> retweet = retweetService.retweetPostList(page, limit, MainTweeid);

        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), retweet);
    }
}
