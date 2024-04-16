package com.example.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{
	
	private Map<String,Object> attributes; //oauth2User.getAttributes

	public GoogleUserInfo(Map<String,Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getProvider() {
		// TODO Auto-generated method stub
		return "google";
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return (String) attributes.get("name");
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	
}
