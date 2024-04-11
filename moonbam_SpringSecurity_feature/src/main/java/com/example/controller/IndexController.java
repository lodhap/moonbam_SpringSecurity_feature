package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.dto.Member;
import com.example.service.MemberService;


@Controller
public class IndexController {
	
	@Autowired
	private MemberService memberServie;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
// 
	@PostMapping("/register")
	public String register(Member member) {
//		System.out.println(member);
		String rawPassword = member.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encPassword);
		
		int n = memberServie.register(member);
		return "redirect:/loginForm";
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
