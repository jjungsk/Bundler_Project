package com.ssafy.bundler.repository.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ssafy.bundler.dto.card.response.CardResponseDto;
import com.ssafy.bundler.dto.comment.CommentResponseDto;
import com.ssafy.bundler.repository.CardRepository;
import com.ssafy.bundler.repository.CategoryRepository;
import com.ssafy.bundler.repository.CommentRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.ssafy.bundler.repository.query
 * fileName       : CardQueryRepository
 * author         : modsiw
 * date           : 2023/02/08
 * description    : 카드 조회시에 쓰이는 쿼리 레포지토리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/08        modsiw       최초 생성
 * 2023/02/09		 modsiw		  검색페이지 카드 파라미터 별로 쿼리 작성
 */
@Repository
@RequiredArgsConstructor
public class CardQueryRepository {

	private final EntityManager em;
	private final CommentRepository commentRepository;
	private final CardRepository cardRepository;
	private final CategoryRepository categoryRepository;

	//카드 리스트 다 가져오기
	public List<CardResponseDto> findAllCardByDto_optimization() {
		List<CardResponseDto> result = findCards();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		System.out.println(result.size());
		Map<Long, List<CommentResponseDto>> cardCommentMap = findCardCommentMap(toCardIds(result));

		result.forEach(c -> c.setCommentResponseDtoList(cardCommentMap.get(c.getCardId())));

		return result;
	}

	private List<Long> toCardIds(List<CardResponseDto> result) {
		return result.stream()
			.map(c -> c.getCardId())
			.collect(Collectors.toList());
	}

	private List<CardResponseDto> findCards() {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w", CardResponseDto.class)
			.getResultList();
	}

	public Map<Long, List<CommentResponseDto>> findCardCommentMap(List<Long> cardIds) {

		List<CommentResponseDto> cardComment = em.createQuery(
				"select new com.ssafy.bundler.dto.comment.CommentResponseDto"
					+ "(c.commentId, c.feedId,"
					+ " c.writer.userId, c.writer.userNickname, c.writer.userProfileImage,"
					+ " c.commentContent, c.createdAt)"
					+ " from Comment c"
					+ " where c.feedId in :feedIds", CommentResponseDto.class)
			.setParameter("feedIds", cardIds)
			.getResultList();

		return cardComment.stream()
			.collect(Collectors.groupingBy(CommentResponseDto::getFeedId));
	}

	// ============ 검색 ============ //

	//=========카드에 키워드 검색 =========//
	public List<CardResponseDto> findAllCardByDto_optimization(String keyword) {
		List<CardResponseDto> result = findCards(keyword);

		Map<Long, List<CommentResponseDto>> cardCommentMap = findCardCommentMap(toCardIds(result));

		result.forEach(c -> c.setCommentResponseDtoList(cardCommentMap.get(c.getCardId())));

		return result;
	}

	private List<CardResponseDto> findCards(String keyword) {
		List<CardResponseDto> feedTitle = em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w"
					+ " where c.feedTitle like :feedTitle", CardResponseDto.class)
			.setParameter("feedTitle", "%" + keyword + "%")
			.getResultList();

		System.out.println(feedTitle.size());

		return feedTitle;
	}

	// ======== 카드에 카테고리 검색 ========= //
	public List<CardResponseDto> findAllCardByDto_optimization(Long categoryId) {

		List<CardResponseDto> result;

		System.out.println("들어옴");
		if (categoryId > 5) {
			result = findCards(categoryId);
		} else {
			result = findCardsByCategoryIdWithChilds(categoryId);
		}

		Map<Long, List<CommentResponseDto>> cardCommentMap = findCardCommentMap(toCardIds(result));

		result.forEach(c -> c.setCommentResponseDtoList(cardCommentMap.get(c.getCardId())));

		return result;
	}

	//카테고리 자식이 있을때 없을때 해야됨

	//카테고리가 자식일 때
	private List<CardResponseDto> findCards(Long categoryId) {
		// System.out.println("들어옴");
		return em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w"
					+ " where c.category.categoryId = :categoryId", CardResponseDto.class)
			.setParameter("categoryId", categoryId)
			.getResultList();
	}

	//카테고리가 부모일때
	private List<CardResponseDto> findCardsByCategoryIdWithChilds(Long categoryId) {
		List<CardResponseDto> categoryId1 = em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w"
					+ " where c.category.parent.categoryId = :categoryId", CardResponseDto.class)
			.setParameter("categoryId", categoryId)
			.getResultList();
		System.out.println(categoryId1.size());
		return categoryId1;
	}

	// ======= 카드에서 카테고리 검색어 같이 ======= //
	public List<CardResponseDto> findAllCardByDto_optimization(Long categoryId, String keyword) {

		List<CardResponseDto> result;

		if (categoryId > 5) {
			System.out.println("hihihihi");
			result = findCards(categoryId, keyword);
		} else {

			System.out.println("hihihihi");
			result = findCardsByCategoryIdWithChilds(categoryId, keyword);
		}

		Map<Long, List<CommentResponseDto>> cardCommentMap = findCardCommentMap(toCardIds(result));

		result.forEach(c -> c.setCommentResponseDtoList(cardCommentMap.get(c.getCardId())));

		return result;
	}

	//카테고리가 자식일 때
	private List<CardResponseDto> findCards(Long categoryId, String keyword) {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w"
					+ " where c.category.categoryId = :categoryId"
					+ " and c.feedTitle like :feedTitle", CardResponseDto.class)
			.setParameter("categoryId", categoryId)
			.setParameter("feedTitle", "%" + keyword + "%")
			.getResultList();
	}

	//카테고리가 부모일때
	private List<CardResponseDto> findCardsByCategoryIdWithChilds(Long categoryId, String keyword) {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.card.response.CardResponseDto"
					+ "(c.cardId, c.createdAt, c.cardType,"
					+ " w.userId, w.userProfileImage, w.userNickname,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.feedTitle, c.feedContent, c.cardDescription, c.cardCommentary,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from Card c"
					+ " join c.writer w"
					+ " where c.category.parent.categoryId = :categoryId"
					+ " and c.feedTitle like :feedTitle", CardResponseDto.class)
			.setParameter("categoryId", categoryId)
			.setParameter("feedTitle", "%" + keyword + "%")
			.getResultList();
	}
}
