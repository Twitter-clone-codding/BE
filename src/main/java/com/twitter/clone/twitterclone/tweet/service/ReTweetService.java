package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import org.springframework.transaction.annotation.Transactional;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.response.ReTweetsListResponse;
import com.twitter.clone.twitterclone.tweet.repository.ReTweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReTweetService {

    private final ReTweetsRepository reTweetsRepository;
    private final TweetLikeRepository likeRepository;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";

    @Transactional(readOnly = true)
    public TweetListAndTotalPageResponse retweetPostList(Integer page, Integer limit, Long tweetId, UserDetailsImpl userDetails) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> retweets = reTweetsRepository.findAllByRetweets_Id(tweetId, pageable);

        List<ReTweetsListResponse> reTweetsListResponseList = retweets.stream()
                .map(retweet -> {
                            int likeTotal = likeRepository.findByTweetId(retweet).size();

                            if (Objects.isNull(likeTotal)) {
                                likeTotal = 0;
                            }

                            return new ReTweetsListResponse(
                                    retweet.getId(),
                                    new TweetUserResponse(
                                            retweet.getUser().getUserId(),
                                            retweet.getUser().getNickname(),
                                            retweet.getUser().getTagName(),
                                            retweet.getUser().getProfileImageUrl()
                                    ),
                                    retweet.getContent(),
                                    retweet.getHashtag(),
                                    likeTotal,
                                    !(likeRepository.findByTweetIdAndEmail(retweet, userDetails.getUser().getEmail()).isEmpty()),
                                    retweet.getViews(),
                                    retweet.getTweetImgList().stream()
                                            .map(fileName -> s3Url + "/" + fileName)
                                            .collect(Collectors.toList()),
                                    retweet.getCreatedAt(),
                                    retweet.getRetweets().getId()
                            );
                        }
                ).collect(Collectors.toList());
        return new TweetListAndTotalPageResponse(reTweetsListResponseList, retweets.getTotalPages());
    }
}
