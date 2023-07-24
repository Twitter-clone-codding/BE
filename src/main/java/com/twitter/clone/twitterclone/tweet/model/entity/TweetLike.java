package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TweetLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweetId")
    private Tweets tweetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweetRecommendationId")
    private Tweets tweetRecommendationId;

    @Column
    private String email;

}
