package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.config.auth.PrincipalDetailsService;
import com.example.config.oauth.PrincipalOauth2UserService;
import com.example.service.BoardUserDetailService;


@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	// 해당 메서드의 리턴되는 오브젝트를 IOC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
//	@Autowired
//	private BoardUserDetailService boardUserDetailService;
	
	@Autowired
	private PrincipalDetailsService principalDetailsService;
	
	@Autowired
	PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
			
			.and()
			.formLogin()
			.loginPage("/loginForm")
			
			.loginProcessingUrl("/login") //login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다.
			.defaultSuccessUrl("/")
			
			.and()
			.oauth2Login()
			.loginPage("/loginForm")
			// 구글 로그인이 완료된 뒤의 후처리 필요함
			// 1. 코드받기(인증)
			// 2. 엑세스토큰(권한)
			// 3. 사용자프로필 정보를 가져와서
			// 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
			// 4-2. 만약 그 정보가 모자라다면 추가적인 회웝가입 창이 필요함
			// Tip. 코드x, 엑세스토큰+사용자프로필정보O
			
			.userInfoEndpoint()
			.userService(principalOauth2UserService); // Oauth2UserService 타입의 객체
		
//		http.userDetailsService(boardUserDetailService);
		http.userDetailsService(principalDetailsService);
	}

}
