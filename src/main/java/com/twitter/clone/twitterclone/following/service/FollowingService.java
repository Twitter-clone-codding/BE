package com.twitter.clone.twitterclone.following.service;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.auth.common.repository.UserRepository;
import com.twitter.clone.twitterclone.following.model.entity.Following;
import com.twitter.clone.twitterclone.following.repository.FollowingRepository;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import com.twitter.clone.twitterclone.tweet.model.response.TweetListAndTotalPageResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetUserResponse;
import com.twitter.clone.twitterclone.tweet.model.response.TweetsListResponse;
import com.twitter.clone.twitterclone.tweet.repository.TweetLikeRepository;
import com.twitter.clone.twitterclone.tweet.repository.TweetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowingService {

    private final FollowingRepository followingRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean followingUser(User user, Long followingUserId) {
        User followingUser = userRepository.findById(
                followingUserId
        ).orElseThrow(); //TODO: 팔로잉할 유저가 없다면 에러 처리

        if (user.getUserId().equals(followingUser.getUserId())) {
            //TODO: 본인한테 팔로워 걸시 에러 처리
            return false;
        }

        Optional<Following> following = followingRepository.findByFollowUserAndUser(followingUser, user);

        if (!following.isEmpty()) {
            followingRepository.delete(following.get());
            return false;
        }
        followingRepository.save(Following.builder()
                .user(user)
                .followUser(followingUser)
                .build());
        return true;
    }



}
