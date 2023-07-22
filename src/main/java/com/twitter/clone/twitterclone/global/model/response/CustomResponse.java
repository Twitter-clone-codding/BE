package com.twitter.clone.twitterclone.global.model.response;

import com.twitter.clone.twitterclone.global.execption.CustomExecption;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class CustomResponse<T> {

    private final String msg;
    private final T result;


    public static ResponseEntity error(CustomExecption errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(errorCode.geterrorMsg());
    }

    public static <T> CustomResponse<T> success(String msg, T result){
        return new CustomResponse<>(msg,result);
    }


}
