package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetsRepository tweetsRepository;
    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";

    @Transactional
    public void tweetDelete(TweetsDeleteRequest request) {
        Tweets tweets = tweetsRepository.findById(request.tweetId())
                .orElseThrow(); //TODO: 에러 처리 해야 함.

        for (String imgFileName : tweets.getTweetImgList()) {

            s3Util.deleteImage(imgFileName);

        }

        tweetsRepository.delete(tweets);
    }

    @Transactional
    public List<TweetsListResponse> tweetPostList(Integer page, Integer limit) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> tweets = tweetsRepository.findAll(pageable);

        List<TweetsListResponse> tweetsListResponses = tweets.stream()
                .filter(a -> a.getRetweets() != null)
                .map(a ->
                        new TweetsListResponse(
                                a.getContent(),
                                a.getHashtag(),
                                0, //TODO 좋아요 갯수 추가 기능.
                                a.getViews(),
                                a.getTweetImgList().stream()
                                        .map(fileName -> s3Url + "/" + fileName)
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        return tweetsListResponses;

    }

}
