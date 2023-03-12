package com.ssafy.bundler.domain;

import static jakarta.persistence.FetchType.*;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
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
@Table(name = "FEED_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = {"feed_id", "category_id"}))
public class FeedCategory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_category_id")
	private Long feedCategoryId;

	@Column(name = "feed_id")
	private Long feedId;

	@Column(name = "target_category_id")
	private Long targetCategoryId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@Builder(toBuilder = true)
	public FeedCategory(Long feedCategoryId, Long feedId, Long targetCategoryId) {
		this.feedCategoryId = feedCategoryId;
		this.feedId = feedId;
		this.targetCategoryId = targetCategoryId;
	}

}
