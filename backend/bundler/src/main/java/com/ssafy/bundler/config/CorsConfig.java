package com.ssafy.bundler.config;// package com.ssafy.bundler.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
// import org.springframework.web.filter.CorsFilter;
//
// @Configuration
// public class CorsConfig {
//
// 	private final long MAX_AGE_SECONDS = 3600;
//
// 	@Bean
// 	public CorsFilter corsFilter() {
// 		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
// 		CorsConfiguration config = new CorsConfiguration();
// 		config.setAllowCredentials(true);
//
// 		config.addAllowedOrigin("http://i8a810.p.ssafy.io:3000");
// 		config.addAllowedOrigin("http://localhost:3000");
// 		config.addAllowedOrigin("http://127.0.0.1:3000");
// 		config.addAllowedHeader("*");
// 		config.addAllowedMethod("*");
//
// 		config.setMaxAge(MAX_AGE_SECONDS);
//
// 		source.registerCorsConfiguration("/**", config);
// 		source.registerCorsConfiguration("/api/**", config);
// 		return new CorsFilter(source);
// 	}
//
// }
