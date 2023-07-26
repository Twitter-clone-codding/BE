package com.twitter.clone.twitterclone.global.advice;

import com.twitter.clone.twitterclone.global.execption.FileExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.FollowingExceptionImpl;
import com.twitter.clone.twitterclone.global.execption.RegisterExceptionImpl;
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

    @ExceptionHandler(FileExceptionImpl.class)
    public ResponseEntity<?> fileErrorHandler(FileExceptionImpl e) {
        return CustomResponse.error(e);
    }


    @ExceptionHandler(FollowingExceptionImpl.class)
    public ResponseEntity<?> followingErrorHandler(FollowingExceptionImpl e){
        return CustomResponse.error(e);
    }

    @ExceptionHandler(RegisterExceptionImpl.class)
    public ResponseEntity<?> registerErrorHandler(RegisterExceptionImpl e){

        return CustomResponse.error(e);
    }

}
