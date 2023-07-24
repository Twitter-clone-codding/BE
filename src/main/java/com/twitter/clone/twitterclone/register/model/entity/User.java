package com.twitter.clone.twitterclone.register.model.entity;


import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    @Column
    private String tagName;
    @Column(nullable = false)
    private String birthday;
    @Column
    private String profileImageUrl;
    @Column
    private String profileBackgroundImageUrl;


//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();

    public User(String email, String password, String nickname, String birthday) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.birthday = birthday;
    }

}
