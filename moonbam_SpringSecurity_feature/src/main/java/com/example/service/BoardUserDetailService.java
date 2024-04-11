package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;
import com.example.dto.Member;
import com.example.security.dto.SecurityUser;

@Service
public class BoardUserDetailService  implements UserDetailsService {
	@Autowired
	MemberDAO dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		System.out.println("BoardUserDetailServie: username==="+ username);
		Member member= dao.userDetail(username);
//		System.out.println("BoardUserDetailServie: dto==="+ member);
		if(member==null) {  //사용자가 없는 경우
			throw new UsernameNotFoundException(username+" 사용자없음");
		}else {
			return new SecurityUser(member);
		}		
	}

}
