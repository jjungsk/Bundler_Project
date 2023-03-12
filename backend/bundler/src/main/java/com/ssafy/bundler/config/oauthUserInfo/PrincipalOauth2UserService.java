package com.ssafy.bundler.config.oauthUserInfo;// package com.ssafy.bundler.config.oauth;
//
// import java.time.LocalDateTime;
// import java.util.Map;
// import java.util.Optional;
//
// import org.springframework.security.authentication.InternalAuthenticationServiceException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;
//
// import com.ssafy.bundler.config.auth.PrincipalDetails;
// import com.ssafy.bundler.config.oauth.provider.FaceBookUserInfo;
// import com.ssafy.bundler.config.oauth.provider.GithubUserInfo;
// import com.ssafy.bundler.config.oauth.provider.GoogleUserInfo;
// import com.ssafy.bundler.config.oauth.provider.NaverUserInfo;
// import com.ssafy.bundler.config.oauth.provider.OAuth2UserInfo;
// import com.ssafy.bundler.domain.ProviderType;
// import com.ssafy.bundler.domain.RoleType;
// import com.ssafy.bundler.domain.User;
// import com.ssafy.bundler.repository.UserRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
//
// 	private final UserRepository userRepository;
//
// 	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
// 	@Override
// 	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
// 		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회
//
// 		// code를 통해 구성한 정보
// 		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
// 		// token을 통해 응답받은 회원정보
// 		System.out.println("oAuth2User : " + oAuth2User);
//
// 		try {
// 			return this.processOAuth2User(userRequest, oAuth2User);
// 		} catch (AuthenticationException ex) {
// 			throw ex;
// 		} catch (Exception ex) {
// 			ex.printStackTrace();
// 			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
// 		}
// 	}
//
// 	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
//
// 		ProviderType providerType = ProviderType.valueOf(
// 			userRequest.getClientRegistration().getRegistrationId().toUpperCase());
//
// 		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
// 		OAuth2UserInfo oAuth2UserInfo = null;
// 		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
// 			System.out.println("구글 로그인 요청~~");
// 			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
// 		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
// 			System.out.println("페이스북 로그인 요청~~");
// 			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
// 		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
// 			System.out.println("네이버 로그인 요청~~");
// 			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
// 		} else if (userRequest.getClientRegistration().getRegistrationId().equals("github")) {
// 			System.out.println("깃헙 로그인 요청~~");
// 			oAuth2UserInfo = new GithubUserInfo(oAuth2User.getAttributes());
// 		} else {
// 			System.out.println("지원하지 않는 플랫폼입니다.");
// 		}
//
// 		//System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
// 		//System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
// 		Optional<User> userOptional =
// 			userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
//
// 		User user;
// 		if (userOptional.isPresent()) {
// 			user = userOptional.get();
// 			// user가 존재하면 update 해주기
// 			user.toBuilder().providerEmail(oAuth2UserInfo.getEmail());
// 			userRepository.save(user);
// 		} else {
// 			// user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
// 			user = User.builder()
// 				// .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
// 				.providerEmail(oAuth2UserInfo.getEmail())
// 				.userRole("ROLE_USER")
// 				.provider(oAuth2UserInfo.getProvider())
// 				.providerId(oAuth2UserInfo.getProviderId())
// 				.build();
// 			userRepository.save(user);
// 		}
//
// 		return new PrincipalDetails(user, oAuth2User.getAttributes());
// 	}
//
// 	private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
// 		LocalDateTime now = LocalDateTime.now();
// 		User user = new User(
// 			userInfo.getId(),
// 			userInfo.getName(),
// 			userInfo.getEmail(),
// 			"Y",
// 			userInfo.getImageUrl(),
// 			providerType,
// 			RoleType.USER,
// 			now,
// 			now
// 		);
//
// 		return userRepository.saveAndFlush(user);
// 	}
//
// 	private User updateUser(User user, OAuth2UserInfo userInfo) {
// 		if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
// 			user.setUsername(userInfo.getName());
// 		}
//
// 		if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
// 			user.setProfileImageUrl(userInfo.getImageUrl());
// 		}
//
// 		return user;
// 	}
// }
