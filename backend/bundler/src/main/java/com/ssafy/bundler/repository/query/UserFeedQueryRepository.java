package com.ssafy.bundler.repository.query;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.ssafy.bundler.dto.bundle.response.BundleResponseDto;
import com.ssafy.bundler.dto.bundle.response.CardBundleQueryDto;
import com.ssafy.bundler.dto.card.response.CardResponseDto;
import com.ssafy.bundler.dto.comment.CommentResponseDto;
import com.ssafy.bundler.dto.user.FollowProfileDto;
import com.ssafy.bundler.dto.user.FollowingListResponseDto;
import com.ssafy.bundler.repository.CommentRepository;
import com.ssafy.bundler.service.FollowService;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

/**
 *packageName    : com.ssafy.bundler.repository.query
 * fileName       : UserFeedQueryRepository
 * author         : modsiw
 * date           : 2023/02/06
 * description    : 사용자의 번들리스트와 카드리스트 조회 서비스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/02/06        modsiw       최초 생성
 * 2023/02/14		 modsiw		  사용자가 팔로잉하는 사람의 번들과 카드만 출력 메서드 추가
 */

@Repository
@RequiredArgsConstructor
public class UserFeedQueryRepository {

	private final EntityManager em;
	private final CommentRepository commentRepository;
	private final FollowService followService;

	//사용자가 팔로잉하는 사람의 번들과 카드만 출력
	public List<CardResponseDto> findCardsByUserIdOnlyFollowing(Long userId) throws Exception {

		//내가 팔로잉한 사람들의 리스트 가져오기
		FollowingListResponseDto userFollowingList = followService.getUserFollowingList(userId);
		List<FollowProfileDto> followingList = userFollowingList.getFollowingList();

		List<Long> collect = followingList.stream().map(f -> f.getUserId()).collect(Collectors.toList());

		List<CardResponseDto> result = findCards(collect);

		Map<Long, List<CommentResponseDto>> cardCommentMap = findCardCommentMap(toCardIds(result));

		result.forEach(c -> c.setCommentResponseDtoList(cardCommentMap.get(c.getCardId())));

		return result;
	}

	private List<Long> toCardIds(List<CardResponseDto> result) {
		return result.stream()
			.map(c -> c.getCardId())
			.collect(Collectors.toList());
	}

	private List<CardResponseDto> findCards(List<Long> followingUserIds) {
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
					+ " where w.userId in :followingUserIds", CardResponseDto.class)
			.setParameter("followingUserIds", followingUserIds)
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

	//////////////

	//사용자가 팔로잉하는 사람의 번들리스트
	public List<BundleResponseDto> findBundlesByUserIdOnlyFollowing(Long userId) {

		//내가 팔로잉한 사람들의 리스트 가져오기
		FollowingListResponseDto userFollowingList = followService.getUserFollowingList(userId);
		List<FollowProfileDto> followingList = userFollowingList.getFollowingList();

		List<Long> collect = followingList.stream().map(f -> f.getFollowId()).collect(Collectors.toList());

		List<BundleResponseDto> result = findBundles(collect);

		Map<Long, List<CardBundleQueryDto>> cardBundleMap = findCardBundleMap(toBundleIds(result));
		Map<Long, List<CommentResponseDto>> bundleCommentMap = findBundleCommentMap(toBundleIds(result));

		//번들들에 속한 카드 리스트들 정보 넣기
		result.forEach(b -> b.setCardBundleQueryDtoList(cardBundleMap.get(b.getBundleId())));
		//번들들에 속한 번들의 댓글 리스트 넣기
		result.forEach(b -> b.setBundleCommentResponseList(bundleCommentMap.get(b.getBundleId())));

		return result;
	}

	//번들 여러개 찾기
	private List<BundleResponseDto> findBundles(List<Long> followingUserIds) {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.bundle.response.BundleResponseDto"
					+ "(b.bundleId, b.createdAt, w.userId, w.userProfileImage, w.userNickname,"
					+ " b.feedTitle, b.feedContent, b.bundleThumbnail, b.bundleThumbnailText,"
					+ " b.feedLikeCnt, b.feedCommentCnt)"
					+ " from Bundle b"
					+ " join b.writer w"
					+ " where b.isBundlePrivate = :isBundlePrivate"
					+ " and w.userId in :followingUserIds", BundleResponseDto.class)
			.setParameter("isBundlePrivate", false)
			.setParameter("followingUserIds", followingUserIds)
			.getResultList();
	}

	//번들 여러개 리스트 아이디들만 뽑아오기
	private List<Long> toBundleIds(List<BundleResponseDto> result) {
		return result.stream()
			.map(b -> b.getBundleId())
			.collect(Collectors.toList());
	}

	//번들 여러개 코멘트 찾기
	public Map<Long, List<CommentResponseDto>> findBundleCommentMap(List<Long> bundleIds) {

		List<CommentResponseDto> bundleComment = em.createQuery(
				"select new com.ssafy.bundler.dto.comment.CommentResponseDto"
					+ "(c.commentId, c.feedId,"
					+ " c.writer.userId, c.writer.userNickname, c.writer.userProfileImage,"
					+ " c.commentContent, c.createdAt)"
					+ " from Comment c"
					+ " where c.feedId in :feedIds", CommentResponseDto.class)
			.setParameter("feedIds", bundleIds)
			.getResultList();

		return bundleComment.stream()
			.collect(Collectors.groupingBy(CommentResponseDto::getFeedId));
	}

	////////////////////////////////

	//카드 포함
	public List<BundleResponseDto> findBundlesByUserIdContainIsBundlePrivate(Long userId) {

		List<BundleResponseDto> result = findBundlesSelf(userId);
		Map<Long, List<CardBundleQueryDto>> cardBundleMap = findCardBundleMap(toBundleIds(result));
		result.forEach(b -> b.setCardBundleQueryDtoList(cardBundleMap.get(b.getBundleId())));

		return result;
	}

	public List<BundleResponseDto> findBundlesByUserIdExceptIsBundlePrivate(Long userId) {

		List<BundleResponseDto> result = findBundlesOther(userId);
		Map<Long, List<CardBundleQueryDto>> cardBundleMap = findCardBundleMap(toBundleIds(result));
		result.forEach(b -> b.setCardBundleQueryDtoList(cardBundleMap.get(b.getBundleId())));

		return result;
	}

	//카드 없이 사용자의 번들 리스트 조회
	public List<BundleResponseDto> findBundlesByUserIdContainIsBundlePrivateSummary(Long userId) {

		return findBundlesSelf(userId);
	}

	public List<BundleResponseDto> findBundlesByUserIdExceptIsBundlePrivateSummary(Long userId) {

		return findBundlesOther(userId);
	}

	//내가 작성한 번들 리스트 (isBundlePrivate 상관없음)
	private List<BundleResponseDto> findBundlesSelf(Long userId) {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.bundle.response.BundleResponseDto"
					+ "(b.bundleId, b.createdAt, w.userId, w.userProfileImage, w.userNickname,"
					+ " b.feedTitle, b.feedContent, b.bundleThumbnail, b.bundleThumbnailText,"
					+ " b.feedLikeCnt, b.feedCommentCnt, b.isBundleDefault, b.isBundlePrivate)"
					+ " from Bundle b"
					+ " join b.writer w"
					+ " where w.userId = :userId ", BundleResponseDto.class)
			.setParameter("userId", userId)
			.getResultList();
	}

	//남이 작성한 번들 리스트 (isBundlePrivate 상관있음)
	private List<BundleResponseDto> findBundlesOther(Long userId) {
		return em.createQuery(
				"select new com.ssafy.bundler.dto.bundle.response.BundleResponseDto"
					+ "(b.bundleId, b.createdAt, w.userId, w.userProfileImage, w.userNickname,"
					+ " b.feedTitle, b.feedContent, b.bundleThumbnail, b.bundleThumbnailText,"
					+ " b.feedLikeCnt, b.feedCommentCnt)"
					+ " from Bundle b"
					+ " join b.writer w"
					+ " where b.isBundlePrivate = :isBundlePrivate"
					+ " and w.userId = :userId ", BundleResponseDto.class)
			.setParameter("isBundlePrivate", false)
			.setParameter("userId", userId)
			.getResultList();
	}

	//카드 번들 엔티티에서 번들 아이디 여러개로 관련 카드들 가져오기
	public Map<Long, List<CardBundleQueryDto>> findCardBundleMap(List<Long> bundleIds) {

		List<CardBundleQueryDto> cardbundle = em.createQuery(
				"select new com.ssafy.bundler.dto.bundle.response.CardBundleQueryDto"
					+ "(cb.bundle.bundleId, c.cardId, c.createdAt, c.cardType, c.writer.userId,"
					+ " c.writer.userProfileImage, c.writer.userNickname, c.feedTitle, c.feedContent,"
					+ " c.category.parent.categoryId, c.category.parent.categoryName,"
					+ " c.category.categoryId, c.category.categoryName,"
					+ " c.cardScrapCnt, c.feedLikeCnt, c.feedCommentCnt)"
					+ " from CardBundle cb"
					+ " join cb.card c"
					+ " where cb.bundle.bundleId in :bundleIds", CardBundleQueryDto.class)
			.setParameter("bundleIds", bundleIds)
			.getResultList();

		return cardbundle.stream()
			.collect(Collectors.groupingBy(CardBundleQueryDto::getBundleId));
	}

}
