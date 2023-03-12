package com.ssafy.bundler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.bundler.domain.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

	@Query("select c from Card c where c.writer.userId = :userId")
	List<Card> findAllByUserId(@Param("userId") Long userId);

}
