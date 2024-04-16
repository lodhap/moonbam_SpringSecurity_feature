package com.example.config.oauth.provider;

import java.util.Map;

// 각 api로 받아오는 데이터의 형식이 다를텐데, 하나의 결과물로 일치시키 위하여 사용하는 인터페이스
// 추가로 각 구현체 필요
public interface OAuth2UserInfo {

	Map<String, Object> getAttributes();
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
}
