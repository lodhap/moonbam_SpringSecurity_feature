package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.config.auth.PrincipalDetails;
import com.example.dto.Member;
import com.example.service.MemberService;


@Controller
public class IndexController {
	
	@Autowired
	private MemberService memberServie;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
// 
	// 일반 로그인 유저정보 조회
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication
			, @AuthenticationPrincipal PrincipalDetails userDetails) {//DI 의존성 주입
		System.out.println("/test/login ==============");
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		System.out.println("authentication: " +principalDetails.getUsername());
		
		System.out.println("userDetails: "+userDetails.getUsername());
		return "세션 정보 확인하기";
	}
	
	// 구글 로그인 유저정보 조회
	// 소셜인지 일반인지에 따라 형식이 맞는 객체로 casting 해줘야 오류가 안남
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication
			, @AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("/test/login ==============");
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		System.out.println("authentication: " +oAuth2User.getAttributes());
		System.out.println("OAuth2User: " +oauth.getAttributes());
		return "OAuth 세션 정보 확인하기";
	}
	
	
	@PostMapping("/register")
	public String register(Member member) {
//		System.out.println(member);
		String rawPassword = member.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encPassword);
		
		int n = memberServie.register(member);
		return "redirect:/loginForm";
	}

	@GetMapping("/info")
	@ResponseBody
	@Secured("ROLE_ADMIN")
	public String info() {
		return "개인정보";
	}
	
	@GetMapping("/data")
	@ResponseBody
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//	@PostAuthorize()
	public String data() {
		return "데이터정보";
	}
	
	
//	Pages
	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	@GetMapping("/registerForm")
	public String registerForm() {
		return "registerForm";
	}
	
	@GetMapping("/")
	@ResponseBody
	public String main() {
		return "main";
	}
	
	@GetMapping("/index")
	@ResponseBody
	public String index() {
		return "index";
	}
	@GetMapping("/user")
	@ResponseBody
	public String user() {
		return "user";
	}
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "manager";
	}
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	
}
