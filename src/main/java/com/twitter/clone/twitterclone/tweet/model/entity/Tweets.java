package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.auth.common.model.entity.User;
import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Tweets extends Auditing {

    @Id
    @GeneratedValue(generator = "tweetId", strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User userId;

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
    @JoinColumn(name = "likes")
    private List<TweetLike> likes;

    public Tweets(TweetsPostRequest tweet, List<String> imageUrl) {
        this.content = tweet.tweet().content();
        this.hashtag = tweet.tweet().hashtag();
        this.views = 0;
        this.tweetImgList = imageUrl;
    }

    public Tweets(TweetsPostRequest tweet, List<String> imageUrl, Tweets mainTweet) {
        this.content = tweet.tweet().content();
        this.hashtag = tweet.tweet().hashtag();
        this.views = 0;
        this.tweetImgList = imageUrl;
        this.retweets = mainTweet;
    }


}
