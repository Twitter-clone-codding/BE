package com.twitter.clone.twitterclone.authlogin.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class LoginRequestDto {


    private String email;

    private String password;

}
