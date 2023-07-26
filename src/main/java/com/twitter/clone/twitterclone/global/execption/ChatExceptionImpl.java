package com.twitter.clone.twitterclone.global.execption;

import com.twitter.clone.twitterclone.global.execption.type.ChatErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ChatExceptionImpl extends RuntimeException implements CustomException{

    private final ChatErrorCode chatErrorCode;

    @Override
    public HttpStatus getHttpStatus() {
        return chatErrorCode.getHttpStatus();
    }

    @Override
    public String getErrorMsg() {
        return chatErrorCode.getErrorMsg();
    }
}
