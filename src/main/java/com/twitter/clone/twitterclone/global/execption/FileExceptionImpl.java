package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.type.FileErrorCode;
import com.twitter.clone.twitterclone.global.execption.type.TweetErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class FileExceptionImpl extends RuntimeException implements CustomException{

    private final FileErrorCode fileErrorCode;


    @Override
    public HttpStatus getHttpStatus() {
        return fileErrorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return fileErrorCode.getErrorMsg();
    }

}


