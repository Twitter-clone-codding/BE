package com.twitter.clone.twitterclone.notification.repository;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.notification.model.entity.Notification;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser(User user, Pageable pageable);
}