package com.example.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.dto.Member;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 session을 만들어줍니다. (키값: Security ContextHolder)
// 오브젝트 타입=> Authentication 타입의 객체
// Authentication 안에 User 정보가 있어야 됨.
// User오브젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails 

public class PrincipalDetails implements UserDetails, OAuth2User{
	
	private Member member; //콤포지션
	private Map<String, Object> attributes;
	
	public PrincipalDetails(Member member) {
		this.member = member;
	}
	public PrincipalDetails(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}

	//해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		member.getRole(); // 유저권한 정보, 하지만 String 타입의 정보이기 때문에 타입변환이 필요
		// 아래와 같이 가공
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return member.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료되지 않았는지 
		return true;//만료되지 않음
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 안잠겼는지
		return true; // 안잠김
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 오래사용되어 만료되지 않았는지
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 1년동안 로그인을 안하면 휴먼계정으로 하기로 함
		// 그럼 false를 통해 휴면계정 관리 기능을 구현 가능
		return true;
	}

	///////////////// OAuth2User 인터페이스 오버라이딩된 함수 2개
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
