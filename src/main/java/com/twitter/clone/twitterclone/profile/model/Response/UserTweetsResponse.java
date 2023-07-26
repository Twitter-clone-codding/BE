package com.twitter.clone.twitterclone.profile.model.Response;

import java.time.LocalDateTime;
import java.util.List;

public record UserTweetsResponse (
        Long id,
        String content,
        String hashtag,
        //Integer hearts,
        //boolean heartsCheck,
        Integer views,
        //이미지
        List<String> imgList,
        LocalDateTime createdAt
)
{
}
