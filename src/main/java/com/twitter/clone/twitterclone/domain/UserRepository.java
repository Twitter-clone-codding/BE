package com.twitter.clone.twitterclone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDomain, Long> {
	Optional<UserDomain> findByEmail(String email);
	Optional<UserDomain> findByGoogleId(String googleId);
}