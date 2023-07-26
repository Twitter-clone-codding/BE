package com.twitter.clone.twitterclone.notice.service;

import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.twitter.clone.twitterclone.notice.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {

//    private final TweetsRepository tweetsRepository;

    public void notifyAddCommentEvent(Tweets tweets) {
        Long userId = tweets.getUser().getUserId(); // 리트윗 달린 게시글의 작성자 pk값
        // 댓글에 대한 처리 후 해당 댓글이 달린 게시글의 pk값으로 게시글을 조회
//        Memo memo = memoRepository.findById(memoId).orElseThrow(
//                () -> new IllegalArgumentException("찾을 수 없는 메모입니다.")
//        );
//        Long userId = memo.getUser().getId();

        if (sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = sseEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name("addComment").data("앙기모띠"));
            } catch (Exception e) {
                sseEmitters.remove(userId);
            }
        }
    }
}