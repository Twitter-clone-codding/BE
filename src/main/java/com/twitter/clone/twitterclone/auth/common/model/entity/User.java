package com.twitter.clone.twitterclone.auth.common.model.entity;

import com.twitter.clone.twitterclone.following.model.entity.Following;
import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import com.twitter.clone.twitterclone.tweet.model.entity.Tweets;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "tagName", length = 50)
    private String tagName;

    @Column(name = "birthday", length = 30)
    private String birthday;

    @Column(name = "profileImageUrl", length = 100)
    private String profileImageUrl;

    @Column(name = "profileBackgroundImageUrl", length = 100)
    private String profileBackgroundImageUrl;

    @Column(name = "googleId", length = 100)
    private String googleId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Tweets> tweetsList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "followUser")
    private List<Following> followUserList;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Following following;

    public User(String name, String email, String encodedPassword, String tagId, String profileImageUrl, String googleId) {
        this.nickname = name;
        this.email = email;
        this.password = encodedPassword;
        this.tagName = tagId;
        this.googleId = googleId;
        this.profileImageUrl = profileImageUrl;
    }

    // 일반 회원가입
    public User(String email, String password, String nickname, String birthday) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public User googleIdUpdate(String googleId) {
        this.googleId = googleId;
        return this;
    }

}
