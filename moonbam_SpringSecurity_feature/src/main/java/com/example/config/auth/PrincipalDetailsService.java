package com.example.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.MemberDAO;
import com.example.dto.Member;
import com.example.security.dto.SecurityUser;


// 시큐리티 설정에서 loginProcessingUrl("/login");
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행
// 이건 그냥 작동 규칙
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	MemberDAO dao;
	
	// 시큐리티 session(내부에 Authentication(내부에 UserDetails가 들어감))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member= dao.userDetail(username);
		if(member==null) {  //사용자가 없는 경우
			throw new UsernameNotFoundException(username+" 사용자없음");
		}else {
			return new SecurityUser(member);
		}	
	}

}
