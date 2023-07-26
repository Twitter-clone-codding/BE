package com.twitter.clone.twitterclone.chatt.repository;

import com.twitter.clone.twitterclone.chatt.model.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findAllBySender(Long senderId);
    List<ChatRoom> findAllByReceiver(Long ReceiverId);

}