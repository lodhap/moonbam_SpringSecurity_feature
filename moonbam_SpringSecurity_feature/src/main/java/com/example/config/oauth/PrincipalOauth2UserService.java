package com.example.config.oauth;

import java.util.Map;

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
import com.example.config.oauth.provider.NaverUserInfo;
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
		// 데이터 확인 코드
		//System.out.println("userRequest: " + userRequest);
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

		// 1. 소셜방식에 따른 oAuth2UsersInfo 생성 및 초기화
		OAuth2UserInfo oAuth2UsersInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
				// attributes를 주면 알아서 만들어주는 객체
			oAuth2UsersInfo = new GoogleUserInfo(oauth2User.getAttributes());
			System.out.println("구글로그인요청");
		} else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){ // 이미 있음
			// {id=yWXGNXr1C_79ijMJkUroXyc1-jDatju2PAp0lUAyYfU, nickname=배성준, email=bsj4387@naver.com, name=배성준}
			oAuth2UsersInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
			System.out.println("네이버로그인요청");
			//System.out.println((Map)oauth2User.getAttributes().get("response"));
		} else {
			System.out.println("우리는 구글과 네이버만 지원해요.");
		}

		// 2. 회원가입을 위한 유저정보 추출&정제
		String provider = oAuth2UsersInfo.getProvider();
		String providerId = oAuth2UsersInfo.getProviderId();
		//System.out.println("providerId: " + providerId);
		String username = oAuth2UsersInfo.getProvider()+"_"+oAuth2UsersInfo.getProviderId();
		String password = bCryptPasswordEncoder.encode("겟인데어");
		String email = oAuth2UsersInfo.getEmail();
		String role = "ROLE_USER";
		
		// 3. 중복확인 (회원가입유무확인)
		// 중복이 아닐시 : member객체 초기화, 회원가입 진행
		Member member = dao.userDetail(username);
		if(member==null) {
			System.out.println("최초 로그인입니다.");
			member = new Member(providerId, username, password, email, role, provider, providerId, null);
			dao.register(member);
		}
		
		// 4. 유저객체와 Map객체가 전달되어 PrincipalDetails 객체가 생성됨
		// 5. 최종적으로 authentication 객체로 들어감
		return new PrincipalDetails(member, oAuth2UsersInfo.getAttributes());
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
