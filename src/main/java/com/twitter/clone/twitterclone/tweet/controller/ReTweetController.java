package com.twitter.clone.twitterclone.tweet.controller;


import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/retweets")
@RequiredArgsConstructor
public class ReTweetController {

    @GetMapping("/{MainTweeid}")
    public CustomResponse<?> getReTweet() {

        return null;
    }
}
