package com.twitter.clone.twitterclone.global.execption.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum SearchErrorCode {

    SEARCH_NULL(HttpStatus.BAD_REQUEST, "검색어가 없습니다.");


    private final HttpStatus httpStatus;
    private final String errorMsg;
}
