package com.twitter.clone.twitterclone.auth.repository;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByEmail(String email);
    Optional<User> findByTagName(String tagName);

    Page<User> findByNicknameContaining(String nickname, Pageable pageable);

}
