package com.twitter.clone.twitterclone.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomOAuthUserService customOAuthUserService;
    private final UserService userService;
    @PostMapping("/api/login")
    public ResponseEntity<OAuth2User> oauthLogin(@RequestBody OAuth2UserRequest userRequest) {
        OAuth2User oauthUser = customOAuthUserService.loadUser(userRequest);
        return ResponseEntity.ok(oauthUser);
    }

    @PostMapping("/api/user/{email}")
    public ResponseEntity<UserDomain> getUser(@PathVariable String email) {
        UserDomain user = userService.selectUser(email);
        return ResponseEntity.ok(user);
    }
}
