package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final S3Util s3Util;

    @PostMapping("/posts")
    public CustomResponse<?> postTweet(
            @RequestPart("test") MultipartFile test
    ) {
        s3Util.saveFile(test,"123464532432dasdas.png");
        return null;
    }

    @GetMapping("/posts")
    public CustomResponse<?> getListTweet(
            @RequestParam Integer page,
            @RequestParam Integer limit
    ) {

        List<TweetsListResponse> tweet = tweetService.tweetPostList(page, limit);

        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), tweet); //TODO: 추가해야함.

    }

    @DeleteMapping("/posts")
    public CustomResponse<?> deleteTweet(
            @RequestBody TweetsDeleteRequest request
    ) {
        tweetService.tweetDelete(request);
        return CustomResponse.success(ResponseMessage.TWEET_DELETE.getMsg(), null);
    }

    @GetMapping("/{MainTweetid}")
    public CustomResponse<?> getDetailTweet() {

        return null;
    }
}
