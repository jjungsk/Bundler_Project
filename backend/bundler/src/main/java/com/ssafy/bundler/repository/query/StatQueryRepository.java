package com.ssafy.bundler.repository.query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.bundler.domain.Feed;
import com.ssafy.bundler.dto.stat.StatCategoryCountDto;
import com.ssafy.bundler.dto.stat.StatMostMakeCategoryDto;
import com.ssafy.bundler.dto.stat.StatTotalCountDto;

public interface StatQueryRepository extends JpaRepository<Feed,Long> {
	@Query(value = "SELECT COUNT(f.feed_id) AS categoryMakeCount ,c.category_id as categoryId ,c.category_name as categoryName,c.category_parent_id as categoryParentId,f.feed_id  "
		+ "FROM CATEGORIES c  "
		+ "LEFT OUTER JOIN CATEGORIES pc ON c.category_parent_id = pc.category_id  "
		+ "LEFT OUTER JOIN CARDS ca ON ca.category_id = c.category_id  "
		+ "LEFT OUTER JOIN(SELECT * FROM FEEDS WHERE user_id=?1) f "
		+ "ON f.feed_id = ca.card_id "
		+ "GROUP BY c.category_id",nativeQuery = true)
	List<StatCategoryCountDto> findFeedJoinCategory(Long userId);

	@Query(value ="SELECT COUNT(*) AS COUNT "
		+ "FROM CARDS c "
		+ "LEFT JOIN FEEDS f ON f.feed_id = c.card_id "
		+ "WHERE user_id=?1"
		,nativeQuery = true
	)
	StatTotalCountDto findCountByUser(Long userid);

	@Query(value = "SELECT COUNT(*) AS count "
		+ "FROM FEED_LIKE fl "
		+ "LEFT JOIN FEEDS f ON fl.feed_id = f.feed_id "
		+ "WHERE f.user_id=?1 "
		+ "GROUP BY f.user_id",nativeQuery = true)
	StatTotalCountDto findLikeTotalCountByUser(Long userId);

	@Query(value = "SELECT SUM(c.card_scrap_cnt) AS count "
		+ "FROM CARDS c "
		+ "LEFT JOIN FEEDS f ON f.feed_id = c.card_id "
		+ "WHERE f.user_id=?1 "
		+ "GROUP BY f.user_id;",nativeQuery = true)
	StatTotalCountDto findScrapCntTotalByUserId(Long userId);

	@Query(value = "SELECT COUNT(f.feed_id) AS count, c.category_id as categoryId, pc.category_name as categoryName, IF(c.category_parent_id IS NULL, c.category_id, c.category_parent_id) AS categoryParentId   "
		+ "FROM CATEGORIES c   "
		+ "LEFT OUTER JOIN CATEGORIES pc ON c.category_parent_id = pc.category_id   "
		+ "LEFT OUTER JOIN CARDS ca ON ca.category_id = c.category_id   "
		+ "LEFT OUTER JOIN(SELECT * FROM FEEDS WHERE user_id=?1) f ON f.feed_id = ca.card_id  "
		+ "GROUP BY categoryParentId HAVING COUNT > 0 "
		+ "ORDER BY COUNT DESC  "
		+ "LIMIT 1",nativeQuery = true)
	StatMostMakeCategoryDto findMostMakeCategory(Long userId);
@Query(value ="SELECT COUNT(f.feed_id) AS count , c.category_id as categoryId, c.category_name as categoryName, c.category_parent_id "
	+ "FROM CATEGORIES c "
	+ "LEFT OUTER JOIN CARDS ca ON ca.category_id = c.category_id "
	+ "LEFT OUTER JOIN (SELECT * FROM FEEDS WHERE user_id=?1) f ON f.feed_id = ca.card_id "
	+ "WHERE c.category_parent_id IS NOT NULL "
	+ "GROUP BY c.category_id "
	+ "HAVING count > 0 "
	+ "ORDER BY count DESC "
	+ "LIMIT 1",nativeQuery = true)
	StatMostMakeCategoryDto findMostMakeSubCategory(Long userId);

@Query(value = "SELECT COUNT(f.follow_to_id) AS COUNT "
	+ "FROM FOLLOWS f "
	+ "LEFT OUTER JOIN FOLLOWS f2 ON f.follow_to_id = f2.follow_from_id "
	+ "WHERE f.follow_from_id = ?1 AND f2.follow_to_id= ?1 "
	+ "GROUP BY f.follow_from_id",nativeQuery = true)
	StatTotalCountDto findMutualFollowCount(Long userId);

@Query(value = "SELECT COUNT(*) AS COUNT "
	+ "FROM  "
	+ "( "
	+ "SELECT IF( SUM(f.feed_like_cnt) IS NULL ,0, SUM(f.feed_like_cnt) ) AS like_cnt "
	+ "FROM USERS u  "
	+ "LEFT OUTER JOIN FEEDS f ON f.user_id = u.user_id "
	+ "GROUP BY u.user_id "
	+ "HAVING like_cnt > ?1 "
	+ ") temp",nativeQuery = true)
	Long countUserHasMoreLike(int like);
@Query(nativeQuery = true,value = "SELECT COUNT(*) AS COUNT "
	+ "FROM  "
	+ "( "
	+ "SELECT u.user_id,u.user_nickname,fw.follow_from_id,fw.follow_to_id, IF( SUM(f.feed_like_cnt) IS NULL ,0, SUM(f.feed_like_cnt) ) AS like_cnt "
	+ "FROM USERS u  "
	+ "LEFT OUTER JOIN FOLLOWS fw ON fw.follow_from_id = u.user_id "
	+ "LEFT OUTER JOIN FEEDS f ON f.user_id = fw.follow_to_id "
	+ "WHERE u.user_id = ?1 "
	+ "GROUP BY fw.follow_to_id "
	+ "HAVING like_cnt > ?2 "
	+ ") temp ")
	Long countUserFollowerHasMoreLike(Long userId, int userLikeTotal);
@Query(nativeQuery = true,value = "SELECT SUM(c.card_scrap_cnt) as count FROM  "
	+ "FEEDS f "
	+ "LEFT JOIN CARDS c ON c.card_id = f.feed_id "
	+ "WHERE f.user_id = ?1 "
	+ "GROUP BY user_id")
	StatTotalCountDto findCardScrapCountByUser(Long userId);

@Query(nativeQuery = true,value = "SELECT COUNT(*) AS COUNT "
	+ "FROM  "
	+ "( "
	+ "SELECT IF( SUM(c.card_scrap_cnt) IS NULL ,0, SUM(c.card_scrap_cnt) ) AS scrap_cnt "
	+ "FROM USERS u  "
	+ "LEFT OUTER JOIN FEEDS f ON f.user_id = u.user_id "
	+ "LEFT JOIN CARDS c ON c.card_id = f.feed_id "
	+ "GROUP BY u.user_id "
	+ "HAVING scrap_cnt > ?1 "
	+ ") temp")
	Long countUserHasMoreCardScrap(int userCardsScrapTotal);
	@Query(nativeQuery = true,value = "SELECT COUNT(*) AS COUNT "
		+ "FROM  "
		+ "( "
		+ "SELECT u.user_id,u.user_nickname,fw.follow_from_id,fw.follow_to_id, IF( SUM(c.card_scrap_cnt) IS NULL ,0, SUM(c.card_scrap_cnt) ) AS like_cnt "
		+ "FROM USERS u  "
		+ "LEFT OUTER JOIN FOLLOWS fw ON fw.follow_from_id = u.user_id "
		+ "LEFT OUTER JOIN FEEDS f ON f.user_id = fw.follow_to_id "
		+ "LEFT JOIN CARDS c ON c.card_id = f.feed_id "
		+ "WHERE u.user_id = ?1 "
		+ "GROUP BY fw.follow_to_id "
		+ "HAVING like_cnt > ?2 "
		+ ") temp ")
	Long countUserFollowerHasMoreCardScrap(Long userId, int userCardsScrapTotal);
@Query(nativeQuery = true,value = "SELECT COUNT(*) count "
	+ "FROM FEEDS f "
	+ "WHERE f.feed_type = 'CARD' AND f.user_id=?1 "
	+ "GROUP BY f.user_id")
	Long countCardWrittenByUserId(Long user);
@Query(nativeQuery = true,value = "SELECT COUNT(*) as COUNT "
	+ " "
	+ "FROM ( "
	+ " "
	+ "SELECT COUNT(*) count "
	+ "FROM FEEDS f "
	+ "WHERE f.feed_type = 'CARD' "
	+ "GROUP BY f.user_id "
	+ "HAVING COUNT > ?1 "
	+ " "
	+ ") temp")
Long countHasCardCntMore(Long count);
}
