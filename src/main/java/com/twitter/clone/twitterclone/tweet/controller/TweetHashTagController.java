package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetHashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashtag/rank")
@RequiredArgsConstructor
public class TweetHashTagController {

    private final TweetHashTagService hashTagService;

    @GetMapping
    public CustomResponse<?> hashTagRankList(){
        return CustomResponse.success(ResponseMessage.TWEET_HASHTAG_RANK.getMsg(), hashTagService.getHashTagList());
    }

}
