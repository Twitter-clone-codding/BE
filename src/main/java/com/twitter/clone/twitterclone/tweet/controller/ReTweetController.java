package com.twitter.clone.twitterclone.tweet.controller;


import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.ReTweetService;
import com.twitter.clone.twitterclone.tweet.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/retweets")
@RequiredArgsConstructor
public class ReTweetController {

    private final ReTweetService retweetService;

//    @GetMapping("/{MainTweeid}")
//    public CustomResponse<?> getListReTweet(
//            @RequestParam Integer page,
//            @RequestParam Integer limit
//    ) {
//
//        List<TweetsListResponse> tweet = retweetService.tweetPostList(page, limit);
//
//        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), tweet); //TODO: 추가해야함.
//    }
}
