package com.twitter.clone.twitterclone.profile.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.profile.model.request.ProfileUpdateRequest;
import com.twitter.clone.twitterclone.profile.model.response.ProfileDetailUser;
import com.twitter.clone.twitterclone.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{tagName}")
    public CustomResponse<?> getProfiles(
            @PathVariable String tagName,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        log.info(tagName);

        ProfileDetailUser profileDetailUser = profileService.getProfiles(tagName, userDetails);
        return CustomResponse.success("프로필을 조회하셨습니다.", profileDetailUser);
    }

    @PutMapping("")
    public CustomResponse<String> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                @ModelAttribute ProfileUpdateRequest profileUpdateRequest) {
//        log.info(userDetails.getUser().getNickname());
//        log.info(userDetails.getUser().getProfileImageUrl());
//        log.info(userDetails.getUser().getProfileBackgroundImageUrl());
//        log.info(userDetails.getUser().getUrl());

        profileService.updateProfile(userDetails, profileUpdateRequest);

        return CustomResponse.success("프로필이 수정되었습니다.", null);
    }
}
