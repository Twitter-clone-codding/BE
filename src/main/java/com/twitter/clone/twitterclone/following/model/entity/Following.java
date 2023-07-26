package com.twitter.clone.twitterclone.following.model.entity;

import com.twitter.clone.twitterclone.auth.model.entity.User;
import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Following extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User followUser;

    @OneToOne
    private User user;

}
