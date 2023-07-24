package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class TweetRecommendation {

    @Id
    @GeneratedValue(generator = "tweetId", strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String hashtag;

    private Integer views;

    @Column
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private List<String> tweetImgList;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tweetId")
    private Tweets retweets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tweetId")
    private List<TweetLike> likes;
}
