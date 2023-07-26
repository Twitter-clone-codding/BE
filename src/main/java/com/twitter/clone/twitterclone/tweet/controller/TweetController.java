package com.twitter.clone.twitterclone.tweet.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.notification.service.NotificationService;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.tweet.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final NotificationService notificationService;

    @GetMapping("/following/list")
    public CustomResponse<?> followingTweetList(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TweetListAndTotalPageResponse listAndTotalPageResponse = tweetService.followingTweetPostList(page, limit, userDetails);
        return CustomResponse.success("성공적으로 트윗 조회를 하셨습니다.", listAndTotalPageResponse);
    }


    @PostMapping(value = "/posts")
    public CustomResponse<TweetsResponse> postTweet(
            @RequestPart TweetsPostRequest TweetsPostRequest,
            @RequestPart(required = false) List<MultipartFile> img,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        TweetsResponse tweets = tweetService.postTweet(TweetsPostRequest, img, userDetails);

        return CustomResponse.success(ResponseMessage.TWEET_POST.getMsg(), tweets);
    }

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
        TweetsResponse detailTweet = tweetService.getDetailTweet(MainTweetid, userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_DETAIL.getMsg(), detailTweet);
    }

}
