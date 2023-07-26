package com.twitter.clone.twitterclone.auth.common.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusCode {

    private int statusCode;
    private String msg;

    public StatusCode(int statusCode, String responseMsg) {
        this.statusCode = statusCode;
        this.msg = responseMsg;
    }

}