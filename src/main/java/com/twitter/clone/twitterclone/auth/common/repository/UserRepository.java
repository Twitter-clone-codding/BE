package com.twitter.clone.twitterclone.auth.common.repository;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByGoogleId(String googleId);
    Optional<User> findByEmail(String email);
<<<<<<< HEAD

=======
    Optional<User> findByTagName(String tagName);
>>>>>>> develop
}
