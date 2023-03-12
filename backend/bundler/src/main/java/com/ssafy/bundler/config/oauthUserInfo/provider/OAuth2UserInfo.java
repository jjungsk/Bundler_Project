package com.ssafy.bundler.config.oauthUserInfo.provider;

import java.util.Map;

public abstract class OAuth2UserInfo {

	public static final String BUNDLER_EMAIL_DOMAIN = "@bundler.com";

	protected Map<String, Object> attributes;

	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public abstract String getId();

	public abstract String getName();

	public abstract String getEmail();

	public abstract String getImageUrl();
	
}
