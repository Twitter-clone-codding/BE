package com.twitter.clone.twitterclone.register.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusCode {

    private int statusCode;
    private String responseMsg;

    public StatusCode(int statusCode, String responseMsg) {
        this.statusCode = statusCode;
        this.responseMsg = responseMsg;
    }

}