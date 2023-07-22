package com.twitter.clone.twitterclone.tweet.service;


import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetService2 {

    private final TweetsRepository2 tweetsRepository2;
    private final S3Util s3Util;

    public void postTweet(TweetsPostRequest tweet) { // 로그인 완성후에 작성자 추가 해야함****

        if(tweet.mainTweetId() == null) { // 메인 트윗일 경우
            // 파일저장
            List<String> imgUrl = s3Util.saveListFile(tweet.img());

            // 메인 트윗을 저장
            tweetsRepository2.save(new Tweets(tweet, imgUrl));

        }
        // 메인 트윗을 찾아서
        Tweets mainTweet = tweetsRepository2.findById(tweet.mainTweetId()).orElseThrow(
                () -> new IllegalArgumentException("해당 트윗이 존재하지 않습니다.")
        );
        // 파일저장
        List<String> imgUrl = s3Util.saveListFile(tweet.img());
        // 리트윗을 저장
        tweetsRepository2.save(new Tweets(tweet, imgUrl, mainTweet));

    }

    public TweetsResponse getDetailTweet(Long mainTweetid) { // 유저 추가는 나중에
        // 메인 트윗 유무
        Tweets tweets = tweetsRepository2.findById(mainTweetid).orElseThrow(
                () -> new IllegalArgumentException("해당 트윗이 존재하지 않습니다.")
        );
        return new TweetsResponse(
                tweets.getContent()
                , tweets.getHashtag()
                , tweets.getViews()
                , tweets.getTweetImgList()
                , tweets.getCreatedAt()
        );
    }
}
