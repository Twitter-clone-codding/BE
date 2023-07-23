package com.twitter.clone.twitterclone.tweet.service;

import com.twitter.clone.twitterclone.global.util.S3Util;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.response.ReTweetsListResponse;
import com.twitter.clone.twitterclone.tweet.repository.ReTweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReTweetService {

    private final ReTweetsRepository reTweetsRepository;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";
    public List<ReTweetsListResponse> retweetPostList(Integer page, Integer limit) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> retweets = reTweetsRepository.findAll(pageable);

        return retweets.stream()
                .map(retwwet ->
                        new ReTweetsListResponse(
                                retwwet.getContent(),
                                retwwet.getHashtag(),
//                                retwwet.getHearts(),
                                retwwet.getViews(),
                                retwwet.getTweetImgList().stream()
                                        .map(fileName -> s3Url + "/" + fileName)
                                        .collect(Collectors.toList()),
                                retwwet.getCreatedAt()
                        )
                )
                .collect(Collectors.toList());
    }
}
