package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
public class Tweets extends Auditing {

    @Id
    @GeneratedValue(generator = "tweetId", strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User userId;

    private String hashtag;

    private String views;

//    private String likes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tweetId")
    private Tweets retweets;
}
