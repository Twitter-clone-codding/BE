package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.tweet.model.entity.TweetLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Long> {
}