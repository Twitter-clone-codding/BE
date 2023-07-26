package com.twitter.clone.twitterclone.notification.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.notification.service.NotificationService;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.type.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @CrossOrigin
    @GetMapping(value = "/notice", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Long userId = userDetails.getUser().getUserId();

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> sseEmitters.remove(userId));
        sseEmitter.onError((e) -> sseEmitters.remove(userId));

        return sseEmitter;
    }

    @GetMapping("/api/notice")
    public CustomResponse<?>  getNotice(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer page,
            @RequestParam Integer limit
    ){
        List<TweetsListResponse> notice = notificationService.getNotice(userDetails.getUser(), page, limit);
        return CustomResponse.success("알림 조회", notice);
    }
}

