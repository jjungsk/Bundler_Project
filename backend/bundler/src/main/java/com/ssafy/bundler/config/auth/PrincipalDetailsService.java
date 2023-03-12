package com.ssafy.bundler.config.auth;// package com.ssafy.bundler.config.auth;
//
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
// import com.ssafy.bundler.domain.User;
// import com.ssafy.bundler.repository.UserRepository;
//
// import lombok.RequiredArgsConstructor;
//
// @Service
// @RequiredArgsConstructor
// public class  PrincipalDetailsService implements UserDetailsService {
//
// 	private final UserRepository userRepository;
//
// 	@Override
// 	public UserDetails loadUserByUsername(String userNickname) throws UsernameNotFoundException {
// 		System.out.println("PrincipalDetailsService : 진입");
// 		User user = userRepository.findByUserNickname(userNickname).orElseThrow();
//
// 		// if(user == null) {
// 		// 	return null;
// 		// }else {
// 		// 	return new PrincipalDetails(user);
// 		// }
//
// 		return new PrincipalDetails(user);
// 	}
// }
