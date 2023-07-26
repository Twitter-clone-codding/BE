package com.twitter.clone.twitterclone.profile.repository;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findByTagName(String tagName);

   // Optional<User> findbyNickname(String nickname);
    //List<Tweets> findAllById(Long id);
}
