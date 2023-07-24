package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.tweet.model.entity.TweetLike;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetHeartService {

    private final TweetLikeRepository likeRepository;
    private final TweetsRepository tweetsRepository;

    public boolean checkTweetLike(Long tweetId, UserDetailsImpl userDetails) {
        Tweets tweet = tweetsRepository.findById(tweetId)
                .orElseThrow(); //TODO: 에러 처리
        Optional<TweetLike> tweetLike = likeRepository.findByTweetIdAndEmail(tweet, userDetails.getUser().getEmail());

        if (!tweetLike.isEmpty()) {
            likeRepository.delete(tweetLike.get());
            return false;
        }
        likeRepository.save(TweetLike.builder()
                .email(userDetails.getUser().getEmail())
                .tweetId(tweet)
                .build()
        );
        return true;
    }

}
