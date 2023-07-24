package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.auth.common.repository.UserRepository;
import com.twitter.clone.twitterclone.following.model.entity.Following;
import com.twitter.clone.twitterclone.following.repository.FollowingRepository;
import com.twitter.clone.twitterclone.global.execption.TweetExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.type.TweetErrorCode;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.TweetView;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsDeleteRequest;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetViewRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TweetService {

    private final FollowingRepository followingRepository;
    private final TweetsRepository tweetsRepository;
    private final TweetLikeRepository likeRepository;
    private final TweetViewRepository tweetViewRepository;
    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";


    @Transactional
    public TweetListAndTotalPageResponse followingTweetPostList(Integer page, Integer limit, UserDetailsImpl userDetails) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        List<Following> followinUserList = followingRepository.findAllByFollowUser(userDetails.getUser());

        if (followinUserList.size() == 0){
            //TODO: 팔로워가 없거나 팔로워의 게시글이 없을경우 에러 처리
            return null;
        }

        //누가 팔로워 했는지 그 팔로워가 랜덤으로 가져올수 있게
        Random random = new Random();
        int randomInt = random.nextInt(followinUserList.size());
        Page<Tweets> tweets = tweetsRepository.findAllByUser(followinUserList.get(randomInt).getUser(), pageable);

        List<TweetsListResponse> tweetsListResponses = tweets.stream()
                .filter(a -> a.getRetweets() == null)
                .map(a ->
                        new TweetsListResponse(
                                a.getId(),
                                new TweetUserResponse(
                                        a.getUser().getUserId(),
                                        a.getUser().getNickname(),
                                        a.getUser().getTagName(),
                                        a.getUser().getProfileImageUrl()
                                ),
                                a.getContent(),
                                a.getHashtag(),
                                likeRepository.findByTweetId(a).size(), //TODO 좋아요 갯수 추가 기능.
                                !(likeRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()),
                                a.getViews(),
                                a.getTweetImgList().stream()
                                        .map(fileName -> s3Url + "/" + fileName)
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        return new TweetListAndTotalPageResponse(tweetsListResponses, tweets.getTotalPages());

    }

    @Transactional
    public void tweetDelete(TweetsDeleteRequest request, UserDetailsImpl userDetails) {
        Tweets tweets = tweetsRepository.findById(request.tweetId())
                .orElseThrow(() -> {
                    throw new TweetExceptionImpl(TweetErrorCode.NO_TWEET);
                });

        if (!isEqualUserId(userDetails.getUser().getUserId(), tweets.getUser().getUserId())) {
            throw new TweetExceptionImpl(TweetErrorCode.NOT_MY_TWEET);
        }
        for (String imgFileName : tweets.getTweetImgList()) {
            s3Util.deleteImage(imgFileName);
        }

        tweetsRepository.delete(tweets);
    }

    @Transactional
    public TweetListAndTotalPageResponse tweetPostList(Integer page, Integer limit, UserDetailsImpl userDetails) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> tweets = tweetsRepository.findAll(pageable);

        List<TweetsListResponse> tweetsListResponses = tweets.stream()
                .filter(a -> a.getRetweets() == null)
                .map(a ->
                        new TweetsListResponse(
                                a.getId(),
                                new TweetUserResponse(
                                        a.getUser().getUserId(),
                                        a.getUser().getNickname(),
                                        a.getUser().getTagName(),
                                        a.getUser().getProfileImageUrl()
                                ),
                                a.getContent(),
                                a.getHashtag(),
                                likeRepository.findByTweetId(a).size(), //TODO 좋아요 갯수 추가 기능.
                                !(likeRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()),
                                a.getViews(),
                                a.getTweetImgList().stream()
                                        .map(fileName -> s3Url + "/" + fileName)
                                        .collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        return new TweetListAndTotalPageResponse(tweetsListResponses, tweets.getTotalPages());

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
        // 컨텐트와 이미지가 모두 널이거나 비어 있는지 확인
        if (tweet.tweet().content().trim().isEmpty() && (imgUrl.isEmpty())) {
            throw new TweetExceptionImpl(TweetErrorCode.EMPTY_CONTENT_AND_IMAGE);
        }

        if (!Objects.isNull(tweet.mainTweetId())) { // 메인 트윗 유무
            Tweets mainTweet = tweetsRepository.findById(tweet.mainTweetId()).orElseThrow(
                    () -> new TweetExceptionImpl(TweetErrorCode.NO_TWEET));
            tweetsRepository.save(new Tweets(tweet, imgUrl, mainTweet, userDetails.getUser()));
        } else {
            tweetsRepository.save(new Tweets(tweet, imgUrl, userDetails.getUser()));
        }
    }

    @Transactional
    public TweetsResponse getDetailTweet(Long mainTweetId, UserDetailsImpl userDetails) { // 유저 추가는 나중에
        // 메인 트윗 유무
        Tweets tweets = tweetsRepository.findById(mainTweetId).orElseThrow(
                () -> new TweetExceptionImpl(TweetErrorCode.NO_TWEET)
        );

        TweetView tweetView = tweetViewRepository.findByTweetIdAndUserId(tweets, userDetails.getUser());
        //조회수 카운트 증가 중복
        if (tweetView == null) {
            tweets.setViews(tweets.getViews() + 1);
            tweetViewRepository.save(new TweetView(tweets, userDetails.getUser()));
        }

        // 하트 카운트 조회후에 넣어야함
        return new TweetsResponse(
                tweets.getId(),
                new TweetUserResponse(
                        tweets.getUser().getUserId(),
                        tweets.getUser().getNickname(),
                        tweets.getUser().getTagName(),
                        tweets.getUser().getProfileImageUrl()
                ),
                tweets.getContent(),
                tweets.getHashtag(),
                likeRepository.findByTweetId(tweets).size(), //TODO 좋아요 갯수 추가 기능.
                !(likeRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()),
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
