package com.twitter.clone.twitterclone.profile.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.profile.model.request.ProfileUpdateRequest;
import com.twitter.clone.twitterclone.profile.model.response.ProfileDetailUser;
import com.twitter.clone.twitterclone.profile.model.response.UserTweetsResponse;
import com.twitter.clone.twitterclone.profile.repository.ProfileRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final TweetLikeRepository likeRepository;

    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";
    @Transactional(readOnly = true)
    public ProfileDetailUser getProfiles(String tagName, UserDetailsImpl userDetails) {
        log.info(tagName);
        User user;

        if(tagName.isEmpty() || tagName == null){
            log.info(userDetails.getUser().getEmail());
           user = profileRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                    ()-> new IllegalArgumentException("해당사용자가아닙니다.")
            );
        }else{
            user = profileRepository.findBytagName(tagName).orElseThrow(
                    ()-> new IllegalArgumentException("해당 사용자가 아닙니다.")
            );
        }



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



    public void updateProfile(UserDetailsImpl userDetails, ProfileUpdateRequest profileUpdateRequest) {
        User user = profileRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("")
        );

        if(profileUpdateRequest != null){
            String nickname = profileUpdateRequest.nickname();
            if(nickname == null || nickname.isEmpty()) {
                throw new IllegalArgumentException("");
            }

            if (nickname.length() > 15){
                throw new IllegalArgumentException("닉네임 15자 미만");
            }

            user.setNickname(nickname);

            MultipartFile profileImg = profileUpdateRequest.profileImageUrl();
            if(profileImg != null){
                String profileImageUrl = s3Util.saveFile(profileImg, "profileImg");
                user.setProfileImageUrl(profileImageUrl);
            }

            MultipartFile profileBackgroundImage = profileUpdateRequest.profileBackgroundUrl();
            if(profileBackgroundImage != null) {
                String profileBackgroundImg = s3Util.saveFile(profileBackgroundImage, "profileBackgroundImage");
                user.setProfileBackgroundImageUrl(profileBackgroundImg);
            }

            String url = profileUpdateRequest.url();
            if (url != null) {
                user.setUrl(url);
            }

            String content = profileUpdateRequest.content();
            if(content != null){
                user.setContent(content);
            }

            profileRepository.save(user);
        }
    }
}
