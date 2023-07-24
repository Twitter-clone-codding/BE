package com.twitter.clone.twitterclone.global.execption;

import org.springframework.http.HttpStatus;

public interface CustomException {

    HttpStatus getHttpStatus();
    String getErrorMsg();

}
