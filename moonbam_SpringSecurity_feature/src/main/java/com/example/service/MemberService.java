package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;
import com.example.dto.Member;

@Service
public class MemberService {

	@Autowired
	MemberDAO dao;
	
	public int register(Member member) {
		return dao.register(member);
	}
}
