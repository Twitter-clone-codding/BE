package com.twitter.clone.twitterclone.notification.service;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.global.execption.FollowingExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.type.FollowingErrorCode;
import com.twitter.clone.twitterclone.notification.model.entity.Notification;
import com.twitter.clone.twitterclone.notification.repository.NotificationRepository;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.twitter.clone.twitterclone.notification.controller.NotificationController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {

//    private final TweetsRepository tweetsRepository;

    private final NotificationRepository notificationRepository;
    private final TweetLikeRepository likeRepository;
    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";

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

    @Transactional
    public List<TweetsListResponse> getNotice(User user, Integer page, Integer limit) {

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Notification> allByUserId = notificationRepository.findByUser(user, pageable);

        List<TweetsListResponse> tweetsListResponses = allByUserId.stream()
                .map(a -> {
                    int likeTotal = likeRepository.findByTweetId(a.getTweets()).size();

                    if (Objects.isNull(likeTotal)) {
                        likeTotal = 0;
                    }
                    return new TweetsListResponse(
                            a.getTweets().getId(),
                            new TweetUserResponse(
                                    a.getTweets().getUser().getUserId(),
                                    a.getTweets().getUser().getNickname(),
                                    a.getTweets().getUser().getTagName(),
                                    a.getTweets().getUser().getProfileImageUrl()
                            ),
                            a.getTweets().getContent(),
                            a.getTweets().getHashtag(),
                            likeTotal,
                            !(likeRepository.findByTweetIdAndEmail(a.getTweets(), user.getEmail()).isEmpty()),
                            a.getTweets().getViews(),
                            a.getTweets().getTweetImgList().stream()
                                    .map(fileName -> s3Url + "/" + fileName)
                                    .collect(Collectors.toList()),
                            a.getTweets().getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        return new TweetListAndTotalPageResponse(tweetsListResponses, allByUserId.getTotalPages());;
    }
}