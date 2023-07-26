package com.twitter.clone.twitterclone.auth.controller;

import com.twitter.clone.twitterclone.auth.model.type.ResponseMessage;
import com.twitter.clone.twitterclone.auth.service.LogoutService;
import com.twitter.clone.twitterclone.global.model.response.CustomResponse;
import com.twitter.clone.twitterclone.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public CustomResponse<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logoutService.logout(request, response);
        return CustomResponse.success(ResponseMessage.LOGOUT.getMsg(), null);
    }

}
