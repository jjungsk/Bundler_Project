package com.ssafy.bundler.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bundler.common.ApiResponse;
import com.ssafy.bundler.config.auth.AuthToken;
import com.ssafy.bundler.config.auth.AuthTokenProvider;
import com.ssafy.bundler.config.auth.UserPrincipal;
import com.ssafy.bundler.config.properties.AppProperties;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.domain.UserRefreshToken;
import com.ssafy.bundler.domain.UserRole;
import com.ssafy.bundler.dto.JwtTokenDto;
import com.ssafy.bundler.dto.UserDto;
import com.ssafy.bundler.dto.user.LoginRequestDto;
import com.ssafy.bundler.dto.user.SignupRequestDto;
import com.ssafy.bundler.exception.LoginFailedException;
import com.ssafy.bundler.repository.UserRefreshTokenRepository;
import com.ssafy.bundler.repository.UserRepository;
import com.ssafy.bundler.service.AuthService;
import com.ssafy.bundler.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final AppProperties appProperties;
	private final AuthTokenProvider authTokenProvider;

	//	@Qualifier(value = "customAuthenticationManager")

	// private AuthenticationManager customAuthenticationManager;
	private final UserRepository userRepository;
	private final UserRefreshTokenRepository userRefreshTokenRepository;

	private final AuthService authService;

	// @Autowired
	// private UserService userService;

	private final static long THREE_DAYS_MSEC = 259200000;
	private final static String REFRESH_TOKEN = "refreshToken";

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
		log.debug("/signup 진입");
		return ResponseEntity.ok(authService.signUp(signupRequestDto));
	}

	@PostMapping("/login")
	public ResponseEntity login(
		HttpServletRequest request,
		HttpServletResponse response,
		@RequestBody LoginRequestDto authRequestDto
	) {
		log.info(authRequestDto.getEmail());

		User user = userRepository.findOneByUserEmail(authRequestDto.getEmail())
			.orElseThrow(() -> new LoginFailedException("해당 email을 가진 유저가 없음."));

		log.info("user.getUserPassword() : " + user.getUserPassword());
		log.info("bCryptPasswordEncoder.encode(authRequestDto.getPassword()) : " + bCryptPasswordEncoder.encode(
			authRequestDto.getPassword()));
		log.info("authRequestDto.getPassword() : " + authRequestDto.getPassword());

		if (!bCryptPasswordEncoder.matches(authRequestDto.getPassword(), user.getUserPassword())) {
			throw new LoginFailedException("비밀번호가 일치하지 않음.");
		}

		// Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
		// 	authRequestDto.getEmail(),
		// 	authRequestDto.getPassword()
		// ));

		// SecurityContextHolder.getContext().setAuthentication(authentication);

		UserPrincipal userPrincipal = UserPrincipal.create(user);

		//		Authentication authentication = customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
		//				userPrincipal,
		//				null,
		//				userPrincipal.getAuthorities()
		//		));
		//		authentication.setAuthenticated(true);

		String userEmail = authRequestDto.getEmail();
		//		SecurityContextHolder.getContext().setAuthentication(authentication);
		//		authentication.setAuthenticated(true);

		Date now = new Date();
		AuthToken accessToken = authTokenProvider.createAuthToken(
			String.valueOf(user.getUserId()),
			UserRole.USER.getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
		);

		long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
		AuthToken refreshToken = authTokenProvider.createAuthToken(
			//			appProperties.getAuth().getTokenSecret(),
			String.valueOf(user.getUserId()),
			new Date(now.getTime() + refreshTokenExpiry)
		);

		// userEmail refresh token 으로 DB 확인
		Optional<UserRefreshToken> userRefreshToken = userRefreshTokenRepository.findByUser_UserEmail(userEmail);
		if (userRefreshToken.isEmpty()) {
			log.info("refresh token을 새로 등록 : " + refreshToken.getToken());
			// 없는 경우 새로 등록
			UserRefreshToken newRefreshToken = UserRefreshToken.builder()
				.userId(user.getUserId())
				.refreshToken(refreshToken.getToken())
				.build();
			userRefreshTokenRepository.saveAndFlush(newRefreshToken);
		} else {
			// DB에 refresh 토큰 업데이트
			UserRefreshToken newRefreshToken = userRefreshToken.get();
			newRefreshToken.setRefreshToken(refreshToken.getToken());
			userRefreshTokenRepository.saveAndFlush(newRefreshToken);
		}

		// int cookieMaxAge = (int)refreshTokenExpiry / 60;
		// CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		// CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

		return ResponseEntity.ok(JwtTokenDto.builder()
			.userId(user.getUserId())
			.userEmail(user.getUserEmail())
			.nickname(user.getUserNickname())
			.accessToken(accessToken.getToken())
			.refreshToken(refreshToken.getToken())
			.build()
		);
	}

	// @GetMapping("/refresh")
	// public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
	// 	String accessToken = HeaderUtil.getAccessToken(request);
	// 	String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
	// 		.map(Cookie::getValue)
	// 		.orElse((null));
	//
	// 	return authService.refresh(accessToken, refreshToken);
	// }

	@PostMapping("/refresh")
	public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
		//		// access token 확인
		//		String accessToken = HeaderUtil.getAccessToken(request);
		//		AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
		//		if (!authToken.validate()) {
		//			return ResponseEntity.internalServerError().body(ApiResponse.invalidAccessToken());
		//		}
		//
		//		// expired access token 인지 확인
		//		Claims claims = authToken.getExpiredTokenClaims();
		//		if (claims == null) {
		//			return ResponseEntity.internalServerError().body(ApiResponse.notExpiredTokenYet());
		//		}
		//
		//		String userId = claims.getSubject();
		//		UserRole userRole = UserRole.of(claims.get("role", String.class));

		// refresh token
		String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
			.map(Cookie::getValue)
			.orElse((null));
		AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);

		log.info("authRefreshToken: " + authRefreshToken.getToken());

		//		if (authRefreshToken.validate()) {
		//			log.info("invalid !!!!!!!!!!");
		//			return ResponseEntity.internalServerError().body(ApiResponse.invalidRefreshToken());
		//		}

		log.info("authRefreshToken.getUserId() : " + authRefreshToken.getUserId());

		Long userId = authRefreshToken.getUserId();

		// userId refresh token 으로 DB 확인
		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(
			userId,
			refreshToken);
		//		UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByRefreshToken(
		//			refreshToken
		//		);

		if (userRefreshToken == null) {
			return ResponseEntity.internalServerError().body(ApiResponse.invalidRefreshToken());
		}

		Date now = new Date();
		AuthToken newAccessToken = authTokenProvider.createAuthToken(
			String.valueOf(userId),
			//			userRole.getCode(),
			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
		);

		long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

		// refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
		// 		if (validTime <= THREE_DAYS_MSEC) {
		// 			// refresh 토큰 설정
		// 			long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
		//
		// 			authRefreshToken = authTokenProvider.createAuthToken(
		// //				appProperties.getAuth().getTokenSecret(),
		// 				String.valueOf(userId),
		// 				new Date(now.getTime() + refreshTokenExpiry)
		// 			);
		//
		// 			// DB에 refresh 토큰 업데이트
		// 			userRefreshToken.setRefreshToken(authRefreshToken.getToken());
		// 			userRefreshToken = userRefreshTokenRepository.save(userRefreshToken);
		//
		// 			int cookieMaxAge = (int)refreshTokenExpiry / 60;
		// 			CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		// 			CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
		// 		}

		User u = userRepository.findByUserId(userId).orElseThrow();

		return ResponseEntity.ok(JwtTokenDto.builder()
			.userId(Long.valueOf(String.valueOf(userId)))
			.userEmail(u.getUserEmail())
			.nickname(u.getUserNickname())
			.accessToken(newAccessToken.getToken())
			.build());
	}

	// @PostMapping("/login")
	// public ResponseEntity<JwtTokenDto> login(@RequestBody @Validated LoginRequestDto request) {
	// 	log.debug("AuthController - email : " + request.getEmail());
	//
	// 	return ResponseEntity.ok(
	// 		authService.login(request.getEmail(), request.getPassword()));
	// }

	// @PostMapping("/refresh")
	// public ResponseEntity<JwtToken> refresh(@RequestBody @Validated TokenReIssueRequest request) {
	// 	return ResponseEntity.ok(authService.refresh(request.getRefreshToken()));
	// }

	@GetMapping("/home")
	public String home() {
		return "<h1>home</h1>";
	}

	// Tip : JWT를 사용하면 UserDetailsService를 호출하지 않기 때문에 @AuthenticationPrincipal 사용
	// 불가능.
	// 왜냐하면 @AuthenticationPrincipal은 UserDetailsService에서 리턴될 때 만들어지기 때문이다.

	// 유저 혹은 매니저 혹은 어드민이 접근 가능
	// @GetMapping("/user")
	// public String user(Authentication authentication) {
	// 	PrincipalDetails principal = (PrincipalDetails)authentication.getPrincipal();
	// 	System.out.println("principal : " + principal.getUser().getUserId());
	// 	System.out.println("principal : " + principal.getUser().getUserNickname());
	// 	System.out.println("principal : " + principal.getUser().getUserPassword());
	//
	// 	return "<h1>user</h1>";
	// }

	// 매니저 혹은 어드민이 접근 가능
	// @GetMapping("/manager/reports")
	// public String reports() {
	// 	return "<h1>reports</h1>";
	// }

	// 어드민이 접근 가능
	// @GetMapping("/admin/users")
	// public List<User> users() {
	// 	return userRepository.findAll();
	// }

	// @PostMapping("/signup")
	// public ResponseEntity<?> signup(@RequestBody SignupRequestDto user) {
	// 	return ResponseEntity.ok(userService.createUser(user));
	// }

}
