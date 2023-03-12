package com.ssafy.bundler.domain;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Feed Entity 작성, update메서드 추가
 *
 * @author 이혜지
 * @version 1.0
 */

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Entity
@Table(name = "FEEDS")
// jpa 상속 관계 매핑 - join 전략
@Inheritance(strategy = InheritanceType.JOINED)
//아래 선언하지 않을 시, DTYPE 컬럼이 생성되지 않는다. 부모 클래스에 선언하며, 하위 클래스를 구분하는 용도의 컬럼이다.
@DiscriminatorColumn(name = "feed_type", discriminatorType = DiscriminatorType.STRING)
public class Feed extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_id")
	private Long feedId;

	@Column(name = "feed_title", length = 1000)
	private String feedTitle;

	@Column(name = "feed_content", length = 3000)
	private String feedContent;

	@Column(name = "feed_like_cnt")
	private int feedLikeCnt;

	@Column(name = "feed_comment_cnt")
	private int feedCommentCnt;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id")
	private User writer;

	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "feed_id")
	private List<Comment> commentList = new ArrayList<>();

	// @Builder.Default
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "feed_id")
	private List<FeedCategory> feedCategoryList;

	//===== 로그인 사용자 =====//

	//사용자가 좋아요한 피드
	@Transient
	private boolean isFeedLiked;

	//=== 비즈니스 로직 ===//

	//피드 삭제 isDeleted
	public void deleteFeed() {
		this.isDeleted = true;
	}

	public void like(int i) {
		this.feedLikeCnt += i;
	}
}
