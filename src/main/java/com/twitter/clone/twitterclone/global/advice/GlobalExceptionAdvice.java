package com.twitter.clone.twitterclone.global.advice;

import com.twitter.clone.twitterclone.global.execption.TweetExceptionImpl;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    @ExceptionHandler(TweetExceptionImpl.class)
    public ResponseEntity<?> tweetErrorHandler(TweetExceptionImpl e) {

        return CustomResponse.error(e);
    }

}
