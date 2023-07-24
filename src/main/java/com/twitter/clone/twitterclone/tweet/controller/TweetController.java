package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CustomResponse<String> postTweet(
            @RequestPart TweetsPostRequest TweetsPostRequest,
            @RequestPart(required = false) List<MultipartFile> img,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        tweetService.postTweet(TweetsPostRequest, img, userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_POST.getMsg(), null);
    }

    //이거 제꺼
    @GetMapping("/posts")
    public CustomResponse<TweetListAndTotalPageResponse> getListTweet(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TweetListAndTotalPageResponse tweetListAndTotalPageResponse = tweetService.tweetPostList(page, limit, userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), tweetListAndTotalPageResponse); //TODO: 추가해야함.
    }
    //이거 제꺼
    @DeleteMapping("/posts")
    public CustomResponse<?> deleteTweet(
            @RequestBody TweetsDeleteRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        tweetService.tweetDelete(request, userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_DELETE.getMsg(), null);
    }

    @GetMapping("/{MainTweetid}")
    public CustomResponse<TweetsResponse> getDetailTweet(
            @PathVariable Long MainTweetid,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TweetsResponse detailTweet = tweetService.getDetailTweet(MainTweetid,userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_DETAIL.getMsg(), detailTweet);
    }
}
