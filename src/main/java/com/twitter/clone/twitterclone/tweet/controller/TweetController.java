package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.util.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    @PostMapping("/posts")
    public CustomResponse<?> postTweet() {

        return null;
    }

    @GetMapping("/posts")
    public CustomResponse<?> getTweet() {

        return null;
    }

    @DeleteMapping("/posts")
    public CustomResponse<?> deleteTweet() {

        return null;
    }

    @GetMapping("/{MainTweetid}")
    public CustomResponse<?> getDetailTweet() {

        return null;
    }
}
