package com.twitter.clone.twitterclone.register.model.request;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String email;
    private String emailCode;
}
