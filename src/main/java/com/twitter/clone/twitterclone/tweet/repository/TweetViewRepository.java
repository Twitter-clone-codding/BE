package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.tweet.model.entity.TweetView;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetViewRepository extends JpaRepository<TweetView, Long> {
    TweetView findByTweetIdAndUserId(Tweets tweets, User user);
}