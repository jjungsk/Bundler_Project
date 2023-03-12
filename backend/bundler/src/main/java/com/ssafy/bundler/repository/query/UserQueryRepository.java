package com.ssafy.bundler.repository.query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.user.UserCalendarDto;

public interface UserQueryRepository extends JpaRepository<User,Long> {
	@Query(value = ""
		+ "SELECT COUNT(*) AS value, DATE(f.created_at) AS day "
		+ "FROM FEEDS f "
		+ "WHERE user_id=?1 "
		+ "GROUP BY created_at",nativeQuery = true)
	List<UserCalendarDto> findDayFeedCount(Long userId);
}
