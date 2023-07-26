package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.type.RegisterErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class RegisterExceptionImpl extends RuntimeException implements CustomException{

    private final RegisterErrorCode errorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return errorCode.getErrorMsg();
    }
}
