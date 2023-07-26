package com.twitter.clone.twitterclone.auth.model.response;


import lombok.Getter;

@Getter
public class LoginResponse {
    private String token;
    private String nickname;
    private String tagName;
    private String email;
    private String profileImageUrl;

    public LoginResponse(String token, String nickname, String profileImageUrl, String email, String tagName) {
        this.token = token;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.tagName = tagName;
    }
}
