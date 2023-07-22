package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetsRepository tweetsRepository;
    private final S3Util s3Util;

    @Transactional
    public void tweetDelete(TweetsDeleteRequest request) {
        Tweets tweets = tweetsRepository.findById(request.tweetId())
                .orElseThrow(); //TODO: 에러 처리 해야 함.

        for (String imgFileName : tweets.getTweetImgList()) {

            s3Util.deleteImage(imgFileName);

        }

        tweetsRepository.delete(tweets);
    }

}
