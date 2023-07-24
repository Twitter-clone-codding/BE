package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import jakarta.persistence.*;

@Entity
public class TweetLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Tweets tweetId;

    @Column
    private String email;

}
