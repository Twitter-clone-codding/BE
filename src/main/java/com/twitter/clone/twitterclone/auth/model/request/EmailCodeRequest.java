package com.twitter.clone.twitterclone.auth.model.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String email;
    private String successKey;
}
