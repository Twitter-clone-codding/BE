package com.twitter.clone.twitterclone.following.repository;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.following.model.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends JpaRepository<Following, Long> {

    Optional<Following> findByFollowUserAndUser(User followingUser, User user);
    List<Following> findAllByFollowUser(User user);

}