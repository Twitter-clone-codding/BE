package com.twitter.clone.twitterclone.chatt.service;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.auth.repository.UserRepository;
import com.twitter.clone.twitterclone.chatt.model.entity.ChatRoom;
import com.twitter.clone.twitterclone.chatt.model.response.ChatRoomResponse;
import com.twitter.clone.twitterclone.chatt.repository.ChatRoomRepository;
import com.twitter.clone.twitterclone.global.execption.ChatExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.type.ChatErrorCode;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public void createChatRoom(User user, Long receiver) {
        userRepository.findById(receiver)
                .orElseThrow(() -> {
                    throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                });
        UUID uuid = UUID.randomUUID();
        chatRoomRepository.save(ChatRoom.builder()
                .roomKey(uuid.toString())
                .sender(user.getUserId())
                .receiver(receiver)
                .build());
    }

    public List<ChatRoomResponse> searchProfileList(User user) {
        userRepository.findById(user.getUserId())
                .orElseThrow(() -> {
                    throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                });

        List<ChatRoomResponse> tweetUserResponses = new ArrayList<>();

        List<ChatRoom> senderChatRoomList = chatRoomRepository.findAllBySender(user.getUserId());

        for (ChatRoom chatRoom : senderChatRoomList) {
            User receiver = userRepository.findById(chatRoom.getReceiver())
                    .orElseThrow(() -> {
                        throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                    });
            tweetUserResponses.add(new ChatRoomResponse(receiver.getUserId(), receiver.getNickname(), receiver.getTagName(), receiver.getProfileImageUrl(),chatRoom.getRoomKey()));
        }

        List<ChatRoom> receiverChatRoomList = chatRoomRepository.findAllByReceiver(user.getUserId());

        for (ChatRoom chatRoom : receiverChatRoomList) {
            User receiver = userRepository.findById(chatRoom.getReceiver())
                    .orElseThrow(() -> {
                        throw new ChatExceptionImpl(ChatErrorCode.NO_RECEIVER_USER);
                    });
            tweetUserResponses.add(new ChatRoomResponse(receiver.getUserId(), receiver.getNickname(), receiver.getTagName(), receiver.getProfileImageUrl(),chatRoom.getRoomKey()));
        }

        return tweetUserResponses;
    }

}
