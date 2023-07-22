package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetService;
import com.twitter.clone.twitterclone.tweet.service.TweetService2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    private final TweetService2 tweetService2;

    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<String> postTweet(
            @RequestPart TweetsPostRequest TweetsPostRequest,
            @RequestPart(required = false) List<MultipartFile> img
            ) {
        tweetService2.postTweet(TweetsPostRequest, img);
        return CustomResponse.success(ResponseMessage.TWEET_POST.getMsg(), null);
    }

    @GetMapping("/posts")
    public CustomResponse<?> getListTweet(
            @RequestParam Integer page,
            @RequestParam Integer limit
    ) {

        List<TweetsListResponse> tweet = tweetService.tweetPostList(page, limit);

        return CustomResponse.success(ResponseMessage.TWEET_DELETE.getMsg(), tweet); //TODO: 추가해야함.
    }

    @DeleteMapping("/posts")
    public CustomResponse<?> deleteTweet(
            @RequestBody TweetsDeleteRequest request
    ) {
        tweetService.tweetDelete(request);
        return CustomResponse.success(ResponseMessage.TWEET_DELETE.getMsg(), null);
    }

    @GetMapping("/{MainTweetid}")
    public CustomResponse<TweetsResponse> getDetailTweet(
            @PathVariable Long MainTweetid
    ) {
        TweetsResponse detailTweet = tweetService2.getDetailTweet(MainTweetid);
        return CustomResponse.success(ResponseMessage.TWEET_DETAIL.getMsg(), detailTweet);
    }
}
