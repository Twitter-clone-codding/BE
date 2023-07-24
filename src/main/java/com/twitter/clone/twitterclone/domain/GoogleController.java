package com.twitter.clone.twitterclone.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class GoogleController {

	/**
	 * 로그인 페이지
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	/**
	 * 메인 페이지
	 * @return
	 */
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
}