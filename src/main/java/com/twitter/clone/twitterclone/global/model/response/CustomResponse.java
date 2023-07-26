package com.twitter.clone.twitterclone.global.model.response;

import com.twitter.clone.twitterclone.global.execption.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@RequiredArgsConstructor
public class CustomResponse<T> {

    private final String msg;
    private final T result;


    public static ResponseEntity error(CustomException errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new CustomResponse<>(errorCode.getErrorMsg(), null));
    }

    public static <T> CustomResponse<T> success(String msg, T result) {
        return new CustomResponse<>(msg, result);
    }

    public static ResponseEntity vaildationError(String msg) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse<>(msg, null));
    }

}
