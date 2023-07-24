package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.tweet.model.entity.TweetLike;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TweetsRepository extends JpaRepository<Tweets, Long> {

}