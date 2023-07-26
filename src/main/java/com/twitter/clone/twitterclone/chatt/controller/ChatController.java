package com.twitter.clone.twitterclone.chatt.controller;

import com.twitter.clone.twitterclone.chatt.model.message.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    @MessageMapping("/send/message")
    public void sendMessage(@Payload ChatMessage message){
        System.out.println("? : "+message);
        template.convertAndSend("/sub/1",message);// 따로 처리 url / 메세징
    }

}
