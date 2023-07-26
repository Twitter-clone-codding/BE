package com.twitter.clone.twitterclone.profile.repository;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findBytagName(String tagName);

   // Optional<User> findbyNickname(String nickname);
    //List<Tweets> findAllById(Long id);
}
