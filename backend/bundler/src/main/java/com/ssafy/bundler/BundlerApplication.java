package com.ssafy.bundler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ssafy.bundler.config.properties.AppProperties;
import com.ssafy.bundler.config.properties.CorsProperties;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing을 활성화
@EnableConfigurationProperties({
	AppProperties.class,
	CorsProperties.class
})
public class BundlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BundlerApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}