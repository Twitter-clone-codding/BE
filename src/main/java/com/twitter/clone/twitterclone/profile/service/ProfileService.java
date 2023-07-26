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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final TweetLikeRepository likeRepository;

    private final S3Util s3Util;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";
    @Transactional(readOnly = true)
    public ProfileDetailUser getProfiles(String tagName, UserDetailsImpl userDetails) {

        User user = profileRepository.findBytagName(tagName).orElse(
            user = profileRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                    ()-> new IllegalArgumentException("해당사용자가 없습니다")
            ));



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



//        User checkNickName = profileRepository.findByNickname(nickname).orElseThrow(
//                ()-> new IllegalArgumentException("닉네임을 입력해주세요")
//        );

    public void updateProfile(UserDetailsImpl userDetails, MultipartFile profileImg, MultipartFile profileBackgroundImage) {
        String nickname = userDetails.getUser().getNickname();
        if(nickname == null || nickname.isEmpty()){
            throw new IllegalArgumentException();
        }

        if(nickname.length() > 15){
            throw new IllegalArgumentException("닉네임 15자 미만");
        }

        User user = profileRepository.findById(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("")
        );

        if(profileImg != null){
            String profileImageUrl = s3Util.saveFile(profileImg, "profileImg");
            user.setProfileImageUrl(profileImageUrl);
        }

        if(profileBackgroundImage != null){
            String profileBackgroundImageUrl = s3Util.saveFile(profileBackgroundImage, "profileBackgroundImg");
            user.setProfileBackgroundImageUrl(profileBackgroundImageUrl);
        }

        profileRepository.save(user);
    }
}
