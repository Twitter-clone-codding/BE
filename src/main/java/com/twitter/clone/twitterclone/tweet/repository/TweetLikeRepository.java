package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.tweet.model.entity.TweetLike;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {
    Optional<TweetLike> findByTweetIdAndEmail(Tweets tweets, String email);

    List<TweetLike> findByTweetId(Tweets tweets);

}