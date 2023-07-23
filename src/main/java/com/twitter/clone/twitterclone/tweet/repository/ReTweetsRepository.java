package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReTweetsRepository extends JpaRepository<Tweets, Long> {
}