package com.twitter.clone.twitterclone.global.execption.type;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode {

    NO_FILE_NAME(HttpStatus.ACCEPTED, "파일 이름이 없습니다."),
    NO_IMAGEFILE(HttpStatus.ACCEPTED, "이미지 파일이 아닙니다." );

    private final HttpStatus httpStatus;
    private final String errorMsg;

}
