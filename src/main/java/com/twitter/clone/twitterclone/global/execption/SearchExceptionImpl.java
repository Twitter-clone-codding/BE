package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.CustomException;
import com.twitter.clone.twitterclone.global.execption.type.RegisterErrorCode;
import com.twitter.clone.twitterclone.global.execption.type.SearchErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class SearchExceptionImpl extends RuntimeException implements CustomException {

    private final SearchErrorCode errorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return errorCode.getErrorMsg();
    }
}
