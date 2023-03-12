package com.ssafy.bundler.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.bundler.config.auth.AuthToken;
import com.ssafy.bundler.config.auth.AuthTokenProvider;
import com.ssafy.bundler.util.HeaderUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private final AuthTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String tokenStr = HeaderUtil.getAccessToken(request);

		AuthToken token = tokenProvider.convertAuthToken(tokenStr);

		log.info("token: " + token.getToken());

		if (token.validate()) {
			log.info("유효한 토큰입니다");

			Authentication authentication = tokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.info(SecurityContextHolder.getContext().getAuthentication().toString());
		}
		//		else {
		//			throw new TokenValidFailedException();
		//		}

		filterChain.doFilter(request, response);
	}
}
