package com.twitter.clone.twitterclone.profile.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.profile.model.Response.ProfileDetailUser;
import com.twitter.clone.twitterclone.profile.model.Response.UserTweetsResponse;
import com.twitter.clone.twitterclone.profile.repository.ProfileRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final TweetLikeRepository likeRepository;

    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";
    @Transactional(readOnly = true)
    public ProfileDetailUser getProfiles(Long userId, UserDetailsImpl userDetails) {

        User user = profileRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자 존재하지 않습니다.")
        );


        return new ProfileDetailUser(
              user.getNickname(),
              user.getTagName(),
              user.getProfileImageUrl(),
              user.getProfileBackgroundImageUrl(),
              user.getContent(),
              user.getUrl(),
              user.getCreatedAt(),
              user.getTweetsList().stream()
                      .map(tweets ->
                              new UserTweetsResponse(
                                      tweets.getId(),
                                      tweets.getContent(),
                                      tweets.getHashtag(),
                                      likeRepository.findByTweetId(tweets).size(),
                                      !(likeRepository.findByEmail(userDetails.getUser().getEmail()).isEmpty()),
                                      tweets.getViews(),
                                      tweets.getTweetImgList().stream()
                                              .map(fileName -> s3Url + "/" + fileName)
                                              .collect(Collectors.toList()),
                                      tweets.getCreatedAt()

                              )

                      ).collect(Collectors.toList()));

    }
}