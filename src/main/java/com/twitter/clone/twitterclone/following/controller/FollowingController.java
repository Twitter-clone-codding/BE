package com.twitter.clone.twitterclone.following.controller;

import com.twitter.clone.twitterclone.following.model.type.FollowingResponseMessage;
import com.twitter.clone.twitterclone.following.service.FollowingService;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/following")
@RequiredArgsConstructor
public class FollowingController {

    private final FollowingService followingService;

    @PostMapping("/{followingUserId}")
    public CustomResponse<?> followingUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long followingUserId
    ) {
        String msg = followingService.followingUser(userDetails.getUser(), followingUserId) ? FollowingResponseMessage.FOLLOWING_USER.getMsg() : FollowingResponseMessage.FOLLOWING_USER_CANCEL.getMsg();
        return CustomResponse.success(msg, null);
    }

}
