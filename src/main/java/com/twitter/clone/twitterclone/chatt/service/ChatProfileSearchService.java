package com.twitter.clone.twitterclone.chatt.service;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.auth.repository.UserRepository;
import com.twitter.clone.twitterclone.following.model.entity.Following;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
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
public class ChatProfileSearchService {

    private final UserRepository userRepository;

    public List<TweetUserResponse> searchProfileList(String search) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "modifiedAt");

        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<User> userList = userRepository.findByNicknameContaining(search, pageable);

        return userList.stream()
                .map(user -> new TweetUserResponse(
                        user.getUserId(),
                        user.getNickname(),
                        user.getTagName(),
                        user.getProfileImageUrl()
                ))
                .collect(Collectors.toList());
    }

}
