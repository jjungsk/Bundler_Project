package com.ssafy.bundler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.bundler.config.auth.UserPrincipal;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userRepository.findOneByUserEmail(userEmail)
			.orElseThrow(() -> new UsernameNotFoundException("유저 정보 없음"));

		return UserPrincipal.create(user);
	}
}
