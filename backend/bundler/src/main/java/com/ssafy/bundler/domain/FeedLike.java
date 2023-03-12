package com.ssafy.bundler.domain;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FEED_LIKE", uniqueConstraints = @UniqueConstraint(columnNames = {"feed_id", "user_id"}))
public class FeedLike implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_like_id")
	private Long feedLikeId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "feed_id")
	private Feed feed;

	@Column(name = "user_id")
	private Long userId;

	@Builder
	public FeedLike(Feed feed, Long userId) {
		this.feed = feed;
		this.userId = userId;
	}

}
