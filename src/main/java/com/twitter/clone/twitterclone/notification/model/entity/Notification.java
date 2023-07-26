package com.twitter.clone.twitterclone.notification.model.entity;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(generator = "tweetId", strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "tweetId")
    private Tweets tweets;

    public Notification(User user, Tweets savetweets) {
        this.user = user;
        this.tweets = savetweets;
    }
}
