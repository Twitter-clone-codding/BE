package com.twitter.clone.twitterclone.profile.repository;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<User, Long> {
    //List<Tweets> findAllById(Long id);
}
