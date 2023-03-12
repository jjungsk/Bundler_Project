package com.ssafy.bundler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.bundler.domain.CardBundle;

import jakarta.transaction.Transactional;

public interface CardBundleRepository extends JpaRepository<CardBundle, Long> {

	List<CardBundle> findAllByBundleId(Long bundleId);

	@Query("select cb from CardBundle cb where cb.bundleId = :bundleId and cb.cardId = :cardId")
	CardBundle findCardBundleByBundleIdWithCardId(@Param("bundleId") Long bundleId, @Param("cardId") Long cardId);

	@Query("delete from CardBundle cb where cb.bundleId = :bundleId and cb.cardId = :cardId")
	@Modifying
	@Transactional
	void deleteCardBundleByBundleIdWithCardId(@Param("bundleId") Long bundleId, @Param("cardId") Long cardId);

	@Query("select cb from CardBundle cb where cb.cardId = :feedId")
	List<CardBundle> findAllByCardId(@Param("feedId") Long feedId);
}
