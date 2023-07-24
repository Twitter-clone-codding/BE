package com.twitter.clone.twitterclone.auth.oauthlogin.controller;

import com.twitter.clone.twitterclone.auth.oauthlogin.service.CustomOAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomOAuthUserService  customOAuthUserService;

    @PostMapping("/api/login")
    public ResponseEntity<OAuth2User> oauthLogin(@RequestBody OAuth2UserRequest userRequest) {
        OAuth2User oauthUser = customOAuthUserService.loadUser(userRequest);
        return ResponseEntity.ok(oauthUser);
    }

}
