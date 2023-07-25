package com.twitter.clone.twitterclone.search.repository;

import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchTweetRepository extends JpaRepository<Tweets, Long> {
    Page<Tweets> findByHashtagContaining(Pageable pageable, String search);

    Page<Tweets> findByContentContaining(Pageable pageable, String search);
}