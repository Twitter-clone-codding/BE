package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.tweet.service.TweetHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tweets/heart")
public class TweetHeartController {

    private TweetHeartService heartService;

}
