package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.execption.TweetExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.type.TweetErrorCode;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final TweetsRepository tweetsRepository;
    private final TweetLikeRepository likeRepository;
    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";

    @Transactional
    public void tweetDelete(TweetsDeleteRequest request, UserDetailsImpl userDetails) {
        Tweets tweets = tweetsRepository.findById(request.tweetId())
                .orElseThrow(); //TODO: 에러 처리 해야 함.

        if (!isEqualUserId(userDetails.getUser().getUserId(), tweets.getUser().getUserId())) {
            //TODO : 에러처리
        }
        for (String imgFileName : tweets.getTweetImgList()) {
            s3Util.deleteImage(imgFileName);
        }

        tweetsRepository.delete(tweets);
    }

    @Transactional
    public List<TweetsListResponse> tweetPostList(Integer page, Integer limit) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> tweets = tweetsRepository.findAll(pageable);

        List<TweetsListResponse> tweetsListResponses = tweets.stream()
                .filter(a -> a.getRetweets() == null)
                .map(a ->
                        new TweetsListResponse(
                                new TweetUserResponse(
                                        a.getUser().getUserId(),
                                        a.getUser().getNickname(),
                                        a.getUser().getTagName(),
                                        a.getUser().getProfileImageUrl()
                                ),
                                a.getContent(),
                                a.getHashtag(),
                                likeRepository.findByTweetId(a).size(), //TODO 좋아요 갯수 추가 기능.
                                a.getViews(),
                                a.getTweetImgList().stream()
                                        .map(fileName -> s3Url + "/" + fileName)
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        return tweetsListResponses;

    }

    @Transactional
    public void postTweet(TweetsPostRequest tweet, List<MultipartFile> img, UserDetailsImpl userDetails) {
        /**
         * 이미지 저장후에 게시글이 저장 실패시 이미지 처리 생각해야함
         */
        List<String> imgUrl = Collections.emptyList();
        if (!Objects.isNull(img)) {
            imgUrl = s3Util.saveListFile(img);
        }
        if (!Objects.isNull(tweet.mainTweetId())) { // 메인 트윗 유무
            Tweets mainTweet = tweetsRepository.findById(tweet.mainTweetId()).orElseThrow(
                    () -> new TweetExceptionImpl(TweetErrorCode.NO_TWEET));
            tweetsRepository.save(new Tweets(tweet, imgUrl, mainTweet, userDetails.getUser()));
        } else {
            tweetsRepository.save(new Tweets(tweet, imgUrl, userDetails.getUser()));
        }
    }

    @Transactional(readOnly = true)
    public TweetsResponse getDetailTweet(Long mainTweetid) { // 유저 추가는 나중에
        // 메인 트윗 유무
        Tweets tweets = tweetsRepository.findById(mainTweetid).orElseThrow(
                () -> new TweetExceptionImpl(TweetErrorCode.NO_TWEET)
        );
        // 하트 카운트 조회후에 넣어야함
        return new TweetsResponse(
//                tweets.getId(),
                new TweetUserResponse(
                        tweets.getUser().getUserId(),
                        tweets.getUser().getNickname(),
                        tweets.getUser().getTagName(),
                        tweets.getUser().getProfileImageUrl()
                ),
                tweets.getContent(),
                tweets.getHashtag(),
                likeRepository.findByTweetId(tweets).size(), //TODO 좋아요 갯수 추가 기능.
                tweets.getViews(),
                tweets.getTweetImgList().stream()
                        .map(fileName -> s3Url + "/" + fileName)
                        .collect(Collectors.toList()),
                tweets.getCreatedAt()
        );
    }

    private boolean isEqualUserId(Long tokenInUserId, Long writeUserId) {
        return tokenInUserId == writeUserId;
    }

}
