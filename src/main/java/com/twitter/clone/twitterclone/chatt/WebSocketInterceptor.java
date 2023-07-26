package com.twitter.clone.twitterclone.chatt;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        MessageHeaders headers = message.getHeaders(); //메세지 들어올때 헤더
        MultiValueMap<String, String> map = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            System.out.println("접속함.");
        }
        return message;
    }

}
