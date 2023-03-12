package com.ssafy.bundler.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.repository.UserRepository;

@SpringBootTest
class StatServiceImplTest {

	@Autowired
	StatServiceImpl statServiceImpl;
	@Autowired
	UserRepository userRepository;

	@Test
	void getActivityStatTest() {
		//given
		User user = new User();
		user = userRepository.save(user);

		//when
		String date = statServiceImpl.getRegisterDate(user.getUserId());

		//then
		assertThat(date).isEqualTo(user.getCreatedAt().toLocalDate().toString());
		System.out.println(date);

	}

	@Test
	void getTotalFeedLike() {
		// //given
		// Long userId = 1L;
		//
		// //when
		// int totalCount = statService.getTotalFeedLike(userId);
		// //then
		//
		// assertThat(totalCount).isEqualTo(3);
	}

	@Test
	void getTotalCardScrappedCount() {
		// //given
		// Long userId = 1L;
		//
		// //when
		// int totalCount = statService.getTotalCardScrappedCount(userId);
		// //then
		//
		// assertThat(totalCount).isEqualTo(9);
	}

	@Test
	void getMaxCategories() {
		// //given
		// Long userId = 1L;
		//
		// //when
		// String[] names = statService.getMaxCategories(userId);
		// System.out.println(names);
		// //then
		//
		// assertThat(names.length).isEqualTo(2);
	}

	@Test
	void getMutualFollowCount() {

		// //given
		// Long userId = 4L;
		// //when
		// int count= statService.getMutualFollowCount(userId);
		// //then
		// assertThat(count).isEqualTo(2);
	}
	@Test
	void getRankingFeedLikeWholeTest() {
		// String res = "";
		// //given
		// for (int i = 1; i < 6; i++) {
		// 	Long userId = Long.parseLong(String.valueOf(i));
		// 	double count = statServiceImpl.getRankingFeedLikeWhole(userId);
		// }
		// //when
		// System.out.println(res);
		//
		// //then
		// assertThat(1).isEqualTo(1);
	}

	@Test
	void getRankingFeedLikeFollowersTest() {
		// String res = "";
		// //given
		// for (int i = 1; i < 6; i++) {
		// 	Long userId = Long.parseLong(String.valueOf(i));
		// 	double count = statServiceImpl.getRankingFeedLikeFollowing(userId);
		// 	res +="\n"+ String.valueOf(i) +"::"+ String.valueOf(count);
		// }
		// //when
		// System.out.println(res);
		//
		// //then
		// assertThat(1).isEqualTo(1);
	}

	@Test
	void getRankingCardScrapCntWholeTest() {
		// String res = "";
		// //given
		// for (int i = 1; i < 6; i++) {
		// 	Long userId = Long.parseLong(String.valueOf(i));
		// 	double count = statServiceImpl.getRankingCardScrapCntWhole(userId);
		// }
		//
		// //then
		// assertThat(1).isEqualTo(1);
	}

	@Test
	void getRankingCardScrapCntFollowingTest() {
		// String res = "";
		// //given
		// for (int i = 1; i < 6; i++) {
		// 	Long userId = Long.parseLong(String.valueOf(i));
		// 	double count = statServiceImpl.getRankingCardScrapCntFollowing(userId);
		// }
		//
		// //then
		// assertThat(1).isEqualTo(1);
	}
	@Test
	void  getRankingCardCntWholeTest(){
		// String res = "";
		// //given
		// for (int i = 1; i < 6; i++) {
		// 	Long userId = Long.parseLong(String.valueOf(i));
		// 	double count = statServiceImpl.getRankingCardCntWhole(userId);
		// }
		//
		// //then
		// assertThat(1).isEqualTo(1);
	}
}