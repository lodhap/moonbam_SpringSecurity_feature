package com.example.security.dto;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.example.dto.Member;

public class SecurityUser extends User{

	private static final long serialVersionUID = 1L;

	public SecurityUser(Member member) {
		super(member.getUsername(), member.getPassword(),
				AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}

}
