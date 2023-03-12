package com.ssafy.bundler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.Category;
import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.stat.StatCategoryCountDto;
import com.ssafy.bundler.dto.stat.StatCategoryDto;
import com.ssafy.bundler.dto.stat.StatMostMakeCategoryDto;
import com.ssafy.bundler.dto.stat.StatTotalCountDto;
import com.ssafy.bundler.repository.CategoryRepository;
import com.ssafy.bundler.repository.UserRepository;
import com.ssafy.bundler.repository.query.StatQueryRepository;

@Service
public class StatServiceImpl implements StatService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	StatQueryRepository statQueryRepository;
	@Autowired
	CategoryRepository categoryRepository;

	User getUser(Long userId){
		return userRepository.findById(userId).orElseThrow(
			()->new IllegalArgumentException("해당 사용를 찾을 수 없습니다.")
		);
	}
	@Transactional
	public StatCategoryDto[] getCategoryStat(Long userId){
		User user = getUser(userId);
		List<StatCategoryCountDto> res = statQueryRepository.findFeedJoinCategory(user.getUserId());

		Map<Long,StatCategoryDto> preResult = new HashMap<>();

		StatTotalCountDto cnt = statQueryRepository.findCountByUser(user.getUserId());
		int totalCount = cnt == null ? 0:cnt.getCount();
		for (StatCategoryCountDto re : res) {
			Long categoryParentId = re.getCategoryParentId();
			if (categoryParentId == null) {

				Long count = re.getCategoryMakeCount();
				Long categoryId = re.getCategoryId();
				String categoryName = re.getCategoryName();

				preResult.put(categoryId,
					StatCategoryDto.builder()
						.categoryMakeCount(count)
						.categoryName(categoryName)
						.subCategories(new ArrayList<>())
						.build());

			}
		}
		for (StatCategoryCountDto re : res) {
			Long count = re.getCategoryMakeCount();
			String categoryName = re.getCategoryName();
			Long categoryParentId = re.getCategoryParentId();
			if (categoryParentId != null) {

				StatCategoryDto stat = preResult.get(categoryParentId);

				StatCategoryDto subStat = StatCategoryDto.builder()
					.categoryMakeCount(count)
					.categoryName(categoryName)
					.build();
				stat.getSubCategories().add(subStat);
				stat.setCategoryMakeCount(stat.getCategoryMakeCount() + count);
			}
		}
		for (StatCategoryCountDto re : res) {
			Long categoryParentId = re.getCategoryParentId();
			Long categoryId = re.getCategoryId();
			StatCategoryDto stat = preResult.get(categoryId);
			if (categoryParentId == null) {
				double pProportion = Math.round(stat.getCategoryMakeCount() / (double)totalCount * 100);
				stat.setProportion(pProportion);

				for (StatCategoryDto cStat : stat.getSubCategories()
				) {
					double cProportion = Math.round(cStat.getCategoryMakeCount() / (double)stat.getCategoryMakeCount()*100);
					cStat.setProportion(cProportion);
				}
			}
		}


		return preResult.values().toArray(new StatCategoryDto[0]);
	}
	@Transactional
	public String getRegisterDate(Long userId){
		User user = getUser(userId);
		return user.getCreatedAt().toLocalDate().toString();
	}

	@Transactional
	public Integer getTotalFeedLike(Long userId){
		User user = getUser(userId);

		StatTotalCountDto totalLike = statQueryRepository.findLikeTotalCountByUser(user.getUserId());
		return totalLike == null ? 0 : totalLike.getCount();
	}
	@Transactional
	public Integer getTotalCardScrappedCount(Long userId){
		User user = getUser(userId);
		StatTotalCountDto totalScrap = statQueryRepository.findScrapCntTotalByUserId(user.getUserId());
		return totalScrap == null ? 0 : totalScrap.getCount();
	}
	@Transactional
	public String[] getMaxCategories(Long userId){
		User user = getUser(userId);
		Category[] categories = new Category[2];

		StatMostMakeCategoryDto categoryDto = statQueryRepository.findMostMakeCategory(user.getUserId());
		if(categoryDto!=null){
			Category category = categoryRepository.findById(categoryDto.getCategoryParentId()).orElseThrow(
				()->new IllegalArgumentException("카테고리 조회중 오류가 발생햇습니다.")
			);
			categories[0] = category;


			categoryDto = statQueryRepository.findMostMakeSubCategory(user.getUserId());
			if(categoryDto!=null){
				category = categoryRepository.findById(categoryDto.getCategoryId()).orElseThrow(
					()->new IllegalArgumentException("카테고리 조회중 오류가 발생햇습니다.")
				);
			}

			categories[1] = category;

			return new String[]{categories[0].getCategoryName(),categories[1].getCategoryName()};
		}
		return new String[2];
	}
	@Transactional
	public int getMutualFollowCount(Long userId){
		User user = getUser(userId);
		StatTotalCountDto maxFollowCount = statQueryRepository.findMutualFollowCount(user.getUserId());
		if(maxFollowCount==null) return 0;
		return maxFollowCount.getCount();
	}

	@Override
	public double getRankingFeedLikeWhole(Long userId) {
		User user = getUser(userId);
		//내 좋아요 총합
		StatTotalCountDto totalLike = statQueryRepository.findLikeTotalCountByUser(user.getUserId());
		int userLikeTotal = totalLike==null ? 0: totalLike.getCount();

		//내 좋아요 총합 보다 많은 사람 수
		Long count = statQueryRepository.countUserHasMoreLike(userLikeTotal);

		//전체 회원수
		long totalUserCount = userRepository.count();

		return Math.round(count/(double) totalUserCount * 100);
	}

	@Override
	public double getRankingFeedLikeFollowing(Long userId) {
		User user = getUser(userId);
		//팔로우한  회원수
		int totalUserFollowerCount = user.getFollowingCnt();
		if(totalUserFollowerCount==0) return 100d;

		//내 좋아요 총합
		StatTotalCountDto totalLike = statQueryRepository.findLikeTotalCountByUser(user.getUserId());
		int userLikeTotal = totalLike==null ? 0: totalLike.getCount();
		//팔로워 중에서 내 좋아요 총합 보다 많은 사람 수
		Long count = statQueryRepository.countUserFollowerHasMoreLike(user.getUserId(),userLikeTotal);

		return Math.round(count/(double) totalUserFollowerCount * 100);
	}

	@Override
	public double getRankingCardScrapCntWhole(Long userId) {
		User user = getUser(userId);
		//내 카드 스크랩 총합
		StatTotalCountDto cardsScrap = statQueryRepository.findCardScrapCountByUser(user.getUserId());
		int userCardsScrapTotal = cardsScrap==null ? 0: cardsScrap.getCount();

		//내 카드 스크랩 총합 보다 많은 사람 수
		Long count = statQueryRepository.countUserHasMoreCardScrap(userCardsScrapTotal);

		//전체 회원수
		long totalUserCount = userRepository.count();

		return Math.round(count/(double) totalUserCount * 100);
	}

	@Override
	public double getRankingCardCntWhole(Long userId) {
		User user = getUser(userId);
		Long userCardCnt = statQueryRepository.countCardWrittenByUserId(user.getUserId());
		if(userCardCnt==null) return 100d;
		Long count = statQueryRepository.countHasCardCntMore(userCardCnt);

		//전체 회원수
		long totalUserCount = userRepository.count();

		return Math.round(count/(double) totalUserCount * 100);
	}

	@Override
	public double getRankingCardScrapCntFollowing(Long userId) {
		User user = getUser(userId);
		//팔로우한  회원수
		int totalUserFollowerCount = user.getFollowingCnt();
		if(totalUserFollowerCount==0) return 100d;

		//내 좋아요 총합
		StatTotalCountDto cardsScrap = statQueryRepository.findCardScrapCountByUser(user.getUserId());
		int userCardsScrapTotal = cardsScrap==null ? 0: cardsScrap.getCount();
		//팔로워 중에서 내 카드스크랩 총합 보다 많은 사람 수
		Long count = statQueryRepository.countUserFollowerHasMoreCardScrap(user.getUserId(),userCardsScrapTotal);

		return Math.round(count/(double) totalUserFollowerCount * 100);
	}
}
