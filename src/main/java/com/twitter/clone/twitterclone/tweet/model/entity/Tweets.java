package com.twitter.clone.twitterclone.tweet.model.entity;

import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import com.twitter.clone.twitterclone.tweet.model.request.TweetsPostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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


    public Tweets(TweetsPostRequest tweet, List<String> imageUrl) {
        this.content = tweet.tweet().content();
        this.hashtag = tweet.tweet().hashtag();
        this.views = 0;
        for (String imageUrls : imageUrl) {
            this.tweetImgList.add(imageUrls);
        }
    }

    public Tweets(TweetsPostRequest tweet, List<String> imageName, Tweets mainTweet) {
        this.content = tweet.tweet().content();
        this.hashtag = tweet.tweet().hashtag();
        this.views = 0;
        for (String imageNames : imageName) {
            this.tweetImgList.add(imageNames);
        }
        this.retweets = mainTweet;
    }
}
