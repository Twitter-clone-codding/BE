package com.twitter.clone.twitterclone.notice.service;

import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class NotificationService {

    private final ConcurrentMap<Long, SseEmitter> clients = new ConcurrentHashMap<>();

    public SseEmitter register(UserDetailsImpl userDetails) {
        SseEmitter emitter = new SseEmitter();
        Long userId = userDetails.getUser().getUserId();
        this.clients.put(userId, emitter);

        emitter.onTimeout(() -> this.clients.remove(userId));
        emitter.onCompletion(() -> this.clients.remove(userId));

        return emitter;
    }

    public void notify(Long userId, String message, Object data) {
        SseEmitter emitter = this.clients.get(userId);

        if (emitter != null) {
            try {
                // 알람 디비 저장

                // 클라이언트에게 데이터를 전송한다.
                emitter.send(SseEmitter.event().name("notification").data(message));
            } catch (Exception e) {
                this.clients.remove(userId);
            }
        }
    }
}