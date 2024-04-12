package com.example.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
	// 구글로그인 버튼 클릭 -> 구글 로그인창 -> 로그인완료 -> code를 리턴(OAuth-Client라이브러리) -> AcessToken요청
	// userRequest 정보 -> loadUser함수 효출-> 구글로부터 회원프로필 받아준다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("userRequest: " + userRequest);
		System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); //registrationId로 어떤 OAuth로 로그인했는지 확인가능.
		System.out.println("getAccessToken: " + userRequest.getAccessToken());
		System.out.println("loadUser: " + super.loadUser(userRequest).getAttributes().get("sub"));
		
		//저장
//		OAuth2User oauth2User = super.loadUser(userRequest);
		//회원가입을 강제로 진행해볼 예정
		return super.loadUser(userRequest);
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
