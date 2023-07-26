package com.twitter.clone.twitterclone.chatt.controller;

import com.twitter.clone.twitterclone.chatt.model.entity.ChatRoom;
import com.twitter.clone.twitterclone.chatt.service.ChatRoomService;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public CustomResponse<?> createChatRoom(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long receiver
    ) {
        chatRoomService.createChatRoom(userDetails.getUser(), receiver);
        return CustomResponse.success("성공적으로 채팅방을 만드셨습니다.", null);
    }

    @GetMapping
    public CustomResponse<?> getChatRoomList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return CustomResponse.success("성공적으로 채팅방 목록을 조회하셨습니다.", chatRoomService.searchProfileList(userDetails.getUser()));
    }

}
