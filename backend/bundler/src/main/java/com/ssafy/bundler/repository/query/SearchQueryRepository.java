package com.ssafy.bundler.repository.query;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.ssafy.bundler.repository.query
 * fileName       : SearchQueryRepository
 * author         : modsiw
 * date           : 2023/02/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/09        modsiw       최초 생성
 */
@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {

	private final EntityManager em;
	// private final
}
