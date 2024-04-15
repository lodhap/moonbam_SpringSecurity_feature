package com.example.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dto.Member;

@Repository
public class MemberDAO {
	@Autowired
	SqlSessionTemplate session;
	
	// 모든 컬럼 조회
	public Member userDetail(String username) {
		System.out.println("dao username :"+ username);
		Member member= session.selectOne("userDetail", username);
		System.out.println("dao member: "+ member);
		return member;
	}
	//username, role만 조회
	public Member userDetail2(String username) {
		return session.selectOne("userDetail2", username);
	}
	
	public int register(Member member) {
		return session.insert("register", member);
	}

}
