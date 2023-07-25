package com.twitter.clone.twitterclone.search.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.search.model.response.SearchTweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.search.service.SearchTweetService;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchTweetService searchTweetService;

    @GetMapping("/api/search")
    public CustomResponse<?> search(
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @RequestParam String search,
            @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        SearchTweetListAndTotalPageResponse searchTweetListAndTotalPageResponses = searchTweetService.SearchTweetPostList(page, limit, search,userDetails);
        return CustomResponse.success(ResponseMessage.TWEET_LIST.getMsg(), searchTweetListAndTotalPageResponses);
    }
}
