package com.ssafy.bundler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.bundler.domain.ProviderType;
import com.ssafy.bundler.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByUserNickname(String userNickname);

	Optional<User> findOneByUserEmail(String userEmail);

	boolean existsByUserEmail(String userEmail);

	// Optional<User> findByProviderAndProviderId(String provider, String providerId);

	Optional<User> findOneByUserId(Long userId);

	// Optional<User> findOneByUserIdAnd(Long userId);

	Optional<User> findOneByProviderTypeAndProviderId(ProviderType providerType, String providerId);

	boolean existsByProviderTypeAndProviderId(ProviderType providerType, String providerId);

	List<User> findByUserNicknameContains(String keyword); //사용자 검색

	// User findByUserId(Long userId);

	Optional<User> findByUserId(Long userId);
}
