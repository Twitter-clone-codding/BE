package com.twitter.clone.twitterclone.auth.authlogin.model.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String email;
    private String emailCode;
}
