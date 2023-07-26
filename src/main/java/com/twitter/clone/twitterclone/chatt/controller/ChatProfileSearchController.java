package com.twitter.clone.twitterclone.chatt.controller;

import com.twitter.clone.twitterclone.chatt.service.ChatProfileSearchService;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatProfileSearchController {

    private final ChatProfileSearchService chatProfileSearchService;

    @RequestMapping("/api/search/profile")
    public CustomResponse<?> searchProfile(
            @RequestParam String search
    ){
        return CustomResponse.success("채팅 상대방들을 조회하셨습니다.",chatProfileSearchService.searchProfileList(search));
    }

}
