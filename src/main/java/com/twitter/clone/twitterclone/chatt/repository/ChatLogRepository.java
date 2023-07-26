package com.twitter.clone.twitterclone.chatt.repository;

import com.twitter.clone.twitterclone.chatt.model.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
}