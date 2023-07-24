package com.twitter.clone.twitterclone.auth.oauthlogin.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private String nickname;
    private String profileImage;

    public UserDto(String email, String nickname, String profileImageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImageUrl;
    }
}
