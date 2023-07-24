package com.twitter.clone.twitterclone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserDomain {
	
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

	public UserDomain(String name, String email, String encodedPassword, String tagId, String profileImageUrl, String googleId) {
		this.nickname = name;
		this.email = email;
		this.password = encodedPassword;
		this.tagName = tagId;
		this.googleId = googleId;
		this.profileImageUrl = profileImageUrl;
	}

	public UserDomain googleIdUpdate(String googleId) {
		this.googleId = googleId;
		return this;
	}
}
