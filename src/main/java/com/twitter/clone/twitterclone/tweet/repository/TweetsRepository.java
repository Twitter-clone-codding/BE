package com.twitter.clone.twitterclone.tweet.repository;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetsRepository extends JpaRepository<Tweets, Long> {

    Page<Tweets> findAllByUser(User user, Pageable pageable);

}