package com.twitter.clone.twitterclone.register.repository;

import com.twitter.clone.twitterclone.register.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
