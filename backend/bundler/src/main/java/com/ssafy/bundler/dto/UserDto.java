package com.ssafy.bundler.dto;

import java.io.Serializable;

import com.ssafy.bundler.domain.ProviderType;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.domain.UserRole;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *packageName    : com.ssafy.bundler.security.dto
 * fileName       : UserDto
 * author         : modsiw
 * date           : 2023/02/08
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/08        modsiw       최초 생성
 */
@Getter
@AllArgsConstructor
public class UserDto implements Serializable {

	@Id
	private Long userId;

	private String userEmail;

	private String userNickname;

	private ProviderType providerType;

	private UserRole roleType;

	public static UserDto toEntity(User user) {
		return new UserDto(
			user.getUserId(),
			user.getUserEmail(),
			user.getUserNickname(),
			user.getProviderType(),
			user.getUserRole()
		);
	}
}
