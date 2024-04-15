package com.example.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.config.auth.PrincipalDetails;
import com.example.config.oauth.provider.GoogleUserInfo;
import com.example.config.oauth.provider.OAuth2UserInfo;
import com.example.dao.MemberDAO;
import com.example.dto.Member;

// 서비스 클래스 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	MemberDAO dao;
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
	// 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인완료 -> code를 리턴(OAuth-Client라이브러리) -> AcessToken요청
	// userRequest 정보 -> loadUser함수 효출-> 구글로부터 회원프로필 받아준다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest: " + userRequest);
			//registrationId 프로퍼티로 어떤 OAuth로 로그인했는지 확인가능.
		System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); 
		//System.out.println("getAccessToken: " + userRequest.getAccessToken());
			//accessToken을 이용해 유저정보 요청, 요청값 반환
		System.out.println("loadUser: " + super.loadUser(userRequest).getAttributes());
		//System.out.println("loadUser: " + super.loadUser(userRequest).getAttributes().get("sub"));
		
		//저장
		//accessToken을 이용해 유저정보 요청, 요청값 반환
		OAuth2User oauth2User = super.loadUser(userRequest);
		//System.out.println("getAttributes: "+oauth2User.getAttributes());
		
		// 회원가입을 위한 유저정보 추출&정제
		String provider = userRequest.getClientRegistration().getRegistrationId(); //google 문자열
		String providerId = oauth2User.getAttribute("sub");
		String username = provider+"_"+providerId; //google_@!#%#%!$#!$!#^!
		//System.out.println("============"+ username);
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oauth2User.getAttribute("email");
		String role = "ROLE_USER";
		
		Member member = dao.userDetail(username);
			// 회원가입진행
		OAuth2UserInfo oAuth2UsersInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글로그인요청");
//			member = new Member(null, username, password, email, role, provider, providerId, null);
//			dao.register(member);
			
			// attributes를 주면 알아서 만들어주는 객체
			oAuth2UsersInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){ // 이미 있음
			System.out.println("네이버로그인요청");
			//할게없음
		} else {
			System.out.println("우리는 구글과 페이스북만 지원해요.");
		}
		
		// 유저객체와 Map객체가 전달되어 PrincipalDetails 객체가 생성됨
		// authentication 객체로 들어감
		return new PrincipalDetails(member, oauth2User.getAttributes());
	}
	
//	super.loadUser(userRequest) 반환값
//		Name: [102801361538461832748], 
//		Granted Authorities:
//		[
//			[
//				ROLE_USER, 
//				SCOPE_https://www.googleapis.com/auth/userinfo.email,
//				SCOPE_https://www.googleapis.com/auth/userinfo.profile, 
//				SCOPE_openid
//			]
//		], 
//		User Attributes: 
//		[
//			{
//				sub=102801361538461832748, 
					//회원가입한 id의 primary key
//				name=배성준, 
//				given_name=성준, 
//				family_name=배, 
//				picture=https://lh3.googleusercontent.com/a/ACg8ocLp3plWR-Ie-a9Iz5-zBF12penCMO4-fG67l8T4J8UWlBrDfvw=s96-c,
					//프로필사진
//				email=7834jsb@gmail.com, 
//				email_verified=true, 
					//이메일 인증되었는지
//				locale=ko
//			}
//		]
	
	// 저장할 값
	// super.loadUser(userRequest).getAttributes() 의 값을 가지고 강제 회원가입 진행
	// id, 비번치고 로그인하는 것이 아니니 null만 아니면 됨
	// username = "google_102801361538461832748"
	// password = "암호화(겟인데어)"
	// email = super.loadUser(userRequest).getAttributes().get("sub")
	// role = "ROLE_USER"
	// private String provider;
	// private String providerId;
}
