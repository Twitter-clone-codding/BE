package com.twitter.clone.twitterclone.chatt.model.entity;

import com.twitter.clone.twitterclone.global.model.entity.Auditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ChatRoom extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long UserA;

    @Column
    private Long UserB;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatLog")
//    private List<ChatLog> chatLogList;


}
