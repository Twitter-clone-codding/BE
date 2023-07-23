package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.type.TweetErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class TweetExceptionImpl extends RuntimeException implements CustomException {

    private final TweetErrorCode tweetErrorCode;


    @Override
    public HttpStatus getHttpStatus() {
        return tweetErrorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return tweetErrorCode.getErrorMsg();
    }
}
