package com.twitter.clone.twitterclone.profile.controller;

import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.profile.service.ProfileService;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public CustomResponse<?> getProfiles(@PathVariable Long userId){

        String msg = "프로필을 조회하셨습니다.";
        profileService.getProfiles(userId);
        return CustomResponse.success(msg, profileUser);
    }

    @PutMapping
    public CustomResponse<String>updateProfile(){
        return null;
    }
}
