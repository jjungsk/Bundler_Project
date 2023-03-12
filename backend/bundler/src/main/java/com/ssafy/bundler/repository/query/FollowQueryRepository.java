package com.ssafy.bundler.repository.query;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.bundler.domain.Follow;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {

	private final EntityManager em;

	public List<Follow> findByFollowToId(Long toUserId) {
		return em.createNativeQuery(
				new StringBuilder()
					.append("SELECT f1.follow_id, f1.follow_from_id, f1.follow_to_id, f3.follow_to_id AS followBackId, f1.created_at, f1.updated_at ")
					.append("FROM FOLLOWS f1 ")
						.append("LEFT JOIN (")
							.append("SELECT f2.follow_id, f2.follow_from_id, f2.follow_to_id ")
							.append("FROM FOLLOWS f2 ")
							.append("WHERE f2.follow_from_id = ?) f3 ")
						.append("ON f1.follow_from_id = f3.follow_to_id ")
					.append("WHERE f1.follow_to_id = ?")
					.toString()
				, Follow.class)
			.setParameter(1, toUserId)
			.setParameter(2, toUserId)
			.getResultList();


	}

}
