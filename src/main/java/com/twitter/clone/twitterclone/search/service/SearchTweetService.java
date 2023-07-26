package com.twitter.clone.twitterclone.search.service;

import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.search.model.response.SearchTweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.search.model.response.SearchTweetsResponse;
import com.twitter.clone.twitterclone.search.repository.SearchTweetRepository;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.response.ReTweetsListResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchTweetService {

    private final SearchTweetRepository searchTweetRepository;
    private final TweetLikeRepository likeRepository;

    private String s3Url = "https://twitter-image-storegy.s3.ap-northeast-2.amazonaws.com";

    @Transactional(readOnly = true)
    public SearchTweetListAndTotalPageResponse SearchTweetPostList(Integer page, Integer limit, String search, UserDetailsImpl userDetails) {

        if (search == null) {
            throw new IllegalArgumentException("검색어가 없습니다.");
        }

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");

        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Tweets> searchContaining = null;
        System.out.println("search : " + search);
        System.out.println("search.contains(#): " + search.contains("#"));
        if (search.contains("#")) {
            // 해시태그 검색
            System.out.println("# search");
            searchContaining = searchTweetRepository.findByHashtagContaining(pageable, search); //findByHashtagLike 대소문자 구분 없이 검색
        } else {
            // 컨텐츠 검색
            System.out.println("content search");
            searchContaining = searchTweetRepository.findByContentContaining(pageable, search);
        }

        if (searchContaining.isEmpty()) {
            return new SearchTweetListAndTotalPageResponse(Collections.emptyList(), 0);
        }

        System.out.println("??? : " + searchContaining.get().toList().get(0).getUser());
        List<SearchTweetsResponse> searchTweetsListResponseList = searchContaining.stream()
                .map(searchtweet -> {
                            int likeTotal = likeRepository.findByTweetId(searchtweet).size();

                            if (Objects.isNull(likeTotal)) {
                                likeTotal = 0;
                            }
                            return new SearchTweetsResponse(
                                    searchtweet.getId(),
                                    new TweetUserResponse(
                                            searchtweet.getUser().getUserId(),
                                            searchtweet.getUser().getNickname(),
                                            searchtweet.getUser().getTagName(),
                                            searchtweet.getUser().getProfileImageUrl()
                                    ),
                                    searchtweet.getContent(),
                                    searchtweet.getHashtag(),
                                    likeTotal,
                                    !(Objects.isNull(likeRepository.findByTweetIdAndEmail(searchtweet, userDetails.getUser().getEmail()))),
                                    searchtweet.getViews(),
                                    searchtweet.getTweetImgList().stream()
                                            .map(fileName -> s3Url + "/" + fileName)
                                            .collect(Collectors.toList()),
                                    searchtweet.getCreatedAt()
                            );
                        }
                )
                .collect(Collectors.toList());

        return new SearchTweetListAndTotalPageResponse(searchTweetsListResponseList, searchContaining.getTotalPages());
    }
}
