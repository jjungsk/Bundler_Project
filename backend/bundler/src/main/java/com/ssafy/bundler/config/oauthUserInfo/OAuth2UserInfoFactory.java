package com.ssafy.bundler.config.oauthUserInfo;

import java.util.Map;

import com.ssafy.bundler.config.oauthUserInfo.provider.FacebookOAuth2UserInfo;
import com.ssafy.bundler.config.oauthUserInfo.provider.GithubOAuth2UserInfo;
import com.ssafy.bundler.config.oauthUserInfo.provider.GoogleOAuth2UserInfo;
import com.ssafy.bundler.config.oauthUserInfo.provider.NaverOAuth2UserInfo;
import com.ssafy.bundler.config.oauthUserInfo.provider.OAuth2UserInfo;
import com.ssafy.bundler.domain.ProviderType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
		switch (providerType) {
			case GOOGLE:
				return new GoogleOAuth2UserInfo(attributes);
			case FACEBOOK:
				return new FacebookOAuth2UserInfo(attributes);
			case NAVER:
				return new NaverOAuth2UserInfo(attributes);
			case GITHUB:
				return new GithubOAuth2UserInfo(attributes);
			default:
				throw new IllegalArgumentException("Invalid Provider Type.");
		}
	}
}
