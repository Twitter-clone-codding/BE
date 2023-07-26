package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.type.FollowingErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class FollowingExceptionImpl extends RuntimeException implements CustomException{

    private final FollowingErrorCode followingErrorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return followingErrorCode.getStatus();
    }

    @Override
    public String getErrorMsg() {
        return followingErrorCode.getMsg();
    }
}
